/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.avalon.java.dev.j120.practice.IO;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.avalon.java.dev.j120.practice.entity.Good;
import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum;
import static ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum.CANCELED;
import static ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum.PREPARING;
import static ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum.SHIPPED;
import ru.avalon.java.dev.j120.practice.entity.OrderedItem;
import ru.avalon.java.dev.j120.practice.entity.Person;


/**Для MySQL 8.0.20*/
public class SQLIO {
    private final String url;
    private final String username;
    private final String password;
    private static boolean DBhasTables = false;
    
    public SQLIO(String url, String username, String password) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        DBhasTables = checkDBtables();
        if (!DBhasTables){
            createTables();
        }
    }
    private boolean checkDBtables() throws SQLException {
        String[] tableNames ={"goods","addreses","persons","orders","ordereditems"};
        try (Connection conn = DriverManager.getConnection(url, username, password)){
            DatabaseMetaData metaData = conn.getMetaData();
            for (String tableName : tableNames) {
                ResultSet tables = metaData.getTables(null, null, tableName, null);
                if (!tables.next()){return false;}
            }
        } 
        return true;
    }
    
    private void createTables() throws SQLException{
        try (Connection conn = DriverManager.getConnection(url, username, password)){
            Statement statement = conn.createStatement();
            int rows;
            String sqlString;
            conn.setAutoCommit(false);
            sqlString = "CREATE TABLE `goods` (\n" +
                                "  `article` bigint NOT NULL,\n" +
                                "  `variety` varchar(255) NOT NULL,\n" +
                                "  `color` varchar(255) DEFAULT NULL,\n" +
                                "  `price` decimal(8,2) NOT NULL,\n" +
                                "  `instock` bigint NOT NULL DEFAULT '0',\n" +
                                "  PRIMARY KEY (`article`),\n" +
                                "  UNIQUE KEY `article_UNIQUE` (`article`)\n" +
                                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci\n";
            rows = statement.executeUpdate(sqlString);    
            
            sqlString ="CREATE TABLE `addreses` (\n" +
                        "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                        "  `city` varchar(50) NOT NULL,\n" +
                        "  `street` varchar(50) NOT NULL,\n" +
                        "  `building` varchar(50) NOT NULL,\n" +
                        "  `flat` varchar(50) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `id_UNIQUE` (`id`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci\n";
            rows = statement.executeUpdate(sqlString);    

            sqlString ="CREATE TABLE `persons` (\n" +
                        "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                        "  `name` varchar(100) NOT NULL,\n" +
                        "  `surname` varchar(100) NOT NULL,\n" +
                        "  `address` int NOT NULL,\n" +
                        "  `phoneNumber` varchar(12) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `idpersons_UNIQUE` (`id`),\n" +
                        "  KEY `address_idx` (`address`),\n" +
                        "  CONSTRAINT `address` FOREIGN KEY (`address`) REFERENCES `addreses` (`id`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci\n";
            rows = statement.executeUpdate(sqlString);    
            
            sqlString ="CREATE TABLE `orders` (\n" +
                        "  `orderNumber` int NOT NULL AUTO_INCREMENT,\n" +
                        "  `orderDate` datetime NOT NULL,\n" +
                        "  `person` int NOT NULL,\n" +
                        "  `discount` int NOT NULL,\n" +
                        "  `orderStatus` enum('PREPARING','SHIPPED','CANCELED') NOT NULL DEFAULT 'PREPARING',\n" +
                        "  PRIMARY KEY (`orderNumber`),\n" +
                        "  UNIQUE KEY `orderNumber_UNIQUE` (`orderNumber`),\n" +
                        "  KEY `persons_idx` (`person`),\n" +
                        "  CONSTRAINT `person` FOREIGN KEY (`person`) REFERENCES `persons` (`id`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci\n";
            rows = statement.executeUpdate(sqlString);    
            
            sqlString ="CREATE TABLE `ordereditems` (\n" +
                        "  `id` varchar(45) NOT NULL,\n" +
                        "  `orderNumber` int NOT NULL,\n" +
                        "  `article` bigint NOT NULL,\n" +
                        "  `variety` varchar(255) NOT NULL,\n" +
                        "  `color` varchar(255) DEFAULT NULL,\n" +
                        "  `fixedPrice` decimal(8,2) NOT NULL,\n" +
                        "  `orderedQuantity` bigint NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `id_UNIQUE` (`id`),\n" +
                        "  KEY `orderNumber_idx` (`orderNumber`),\n" +
                        "  CONSTRAINT `orderNumber` FOREIGN KEY (`orderNumber`) REFERENCES `orders` (`orderNumber`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci\n";
            rows = statement.executeUpdate(sqlString);
            conn.commit();
            conn.setAutoCommit(true);
            DBhasTables = true;    
        } 
    }

    public static boolean isDBhasTables() {
        return DBhasTables;
    }
    
    public HashMap<Long, Good> readGoods(){
        try (Connection conn = DriverManager.getConnection(url, username, password)){
            HashMap<Long, Good> goodsMap = new HashMap<>();
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM goods");
            while(resultSet.next()){

                long article = resultSet.getLong(1);
                String variety = resultSet.getString(2);
                String color = resultSet.getString(3);
                BigDecimal price = resultSet.getBigDecimal(4);
                long instock = resultSet.getLong(5);
                
                Good good = new Good(article,variety,color,price,instock);
                goodsMap.putIfAbsent(good.getArticle(), good);
            }
            return goodsMap;
        } catch (SQLException ex) {
            return new HashMap<>();
        }
    }
    
    public HashMap<Long, Order> readOrders(){
        try (Connection conn = DriverManager.getConnection(url, username, password)){
            HashMap<Long, Order> ordersMap = new HashMap<>();
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT " +
                                                        "orderNumber, " +
                                                        "orderDate, " +
                                                        "discount, " + 
                                                        "orderStatus, " + 
                                                        "persons.name as name, " +
                                                        "persons.surname as surname, " +
                                                        "addreses.city as city, " +
                                                        "addreses.street as street, " +
                                                        "addreses.building as building, " +
                                                        "addreses.flat as flat," +
                                                        "persons.phoneNumber as phoneNumber " +
                                                        "FROM orders " +
                                                        "INNER JOIN persons ON orders.person = persons.id " +
                                                        "INNER JOIN addreses ON persons.address = addreses.id");
            while(resultSet.next()){

                long orderNumber = resultSet.getLong("orderNumber");
                LocalDate orderDate = resultSet.getDate("orderDate").toLocalDate();
                
                Person contactPerson = new Person(resultSet.getString("name"), 
                    resultSet.getString("surname"), resultSet.getString("street"), 
                    resultSet.getString("phonenumber") );
                
                int discount = resultSet.getInt("discount");

                String orderStatusString  = resultSet.getString("orderStatus");
                OrderStatusEnum orderStatus = PREPARING;
                switch (orderStatusString){
                    case "PREPARING":
                        orderStatus = PREPARING;
                        break;
                    case "SHIPPED":
                        orderStatus = SHIPPED;
                        break;
                    case "CANCELED":
                        orderStatus = CANCELED;
                        break;
                }
                
                Order order = new Order(orderNumber,orderDate,contactPerson,discount,orderStatus);
                
                
                ordersMap.putIfAbsent(order.getOrderNumber(), order);
                              
            }
            
            return fillOrderedItems(ordersMap);
        } catch (SQLException ex) {
            System.out.println(ex.toString()); 
            return new HashMap<>();
        }
    }
    private HashMap<Long, Order> fillOrderedItems(HashMap<Long, Order> orders){
       
        try (Connection conn = DriverManager.getConnection(url, username, password)){
            
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(
                        "SELECT * FROM storedb.ordereditems");
                
                    while(resultSet.next()){
                        long orderNumber = resultSet.getLong("orderNumber");
                        long article = resultSet.getLong("article");
                        String variety = resultSet.getString("variety");
                        String color = resultSet.getString("color");
                        BigDecimal fixedPrice = resultSet.getBigDecimal("fixedPrice");
                        long orderedQuantity = resultSet.getLong("orderedQuantity");

                        Good good = new Good(article,variety,color,fixedPrice,0);
                        OrderedItem orderedItem = new OrderedItem(good,fixedPrice, orderedQuantity);

                        orders.get(orderNumber).add(orderedItem);
                    
                }
        } catch (SQLException ex) {
            System.out.println(ex.toString()); 
        }
        
        return orders;
    }
    
    public boolean write(HashMap<Long, Good> map){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                
                StringBuilder sqlCommand = new StringBuilder();
                ArrayList<Good> arrayPriceList = new ArrayList<>(map.values());
                arrayPriceList.sort((Good o1, Good o2) -> 
                o1.getArticle()>o2.getArticle()? 1: -1);
                
                for (Good good : arrayPriceList ){
                        sqlCommand.setLength(0);
                        sqlCommand.append("INSERT goods(article, variety, color, price, instock) VALUES (");
                        sqlCommand.append(good.getArticle());
                        sqlCommand.append(", '");
                        sqlCommand.append(good.getVariety().replace('"', '\"'));
                        sqlCommand.append("', '");
                        sqlCommand.append(good.getColor().replace('"', '\"'));
                        sqlCommand.append("', ");
                        sqlCommand.append(good.getPrice().floatValue());
                        sqlCommand.append(", ");
                        sqlCommand.append(good.getInstock());
                        sqlCommand.append(") ");
                        int rows = statement.executeUpdate(sqlCommand.toString());
                }
            }
        }
        catch(ClassNotFoundException ex){
            System.out.println("Connection failed...");            
            System.out.println(ex);
            return false;
        }
        catch(IllegalAccessException | IllegalArgumentException |
               InstantiationException | NoSuchMethodException | SecurityException | 
               InvocationTargetException | SQLException ex){
            System.out.println("Error! ");
            System.out.println(ex);
            return false;
        }
        return true;
    }
}

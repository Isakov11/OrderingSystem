/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.avalon.java.dev.j120.practice.IO;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.avalon.java.dev.j120.practice.entity.Good;


public class SQLIO {
    String url;
    String username;
    String password;
    
    public SQLIO(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        /*url = "jdbc:mysql://localhost/storeDB?serverTimezone=Europe/Moscow";
        username = "root";
        password = "Hanako_290535";*/
    }
    
    public HashMap<Long, Good> read(){
        
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
    
    public boolean write(HashMap<Long, Good> map){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                System.out.println("Connection to Store DB established");
                
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
                        sqlCommand.append(good.getColor());
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

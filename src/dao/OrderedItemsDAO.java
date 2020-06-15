/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.avalon.java.dev.j120.practice.entity.Good;
import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.entity.OrderedItem;

public class OrderedItemsDAO {

    public Order create(Order order,Connection conn) throws SQLException{
        //запись заказанных позиций
        String sqlStatement;
        
        if (!order.getOrderList().isEmpty()){
            sqlStatement = "INSERT ordereditems(" + 
                "orderNumber, article, variety, color, fixedPrice, orderedQuantity) " +
                "VALUES (?,?,?,?,?,?)";
            
            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)) {
                for (OrderedItem orderedItem : order.getOrderList().values()){
                    preparedStatement.setLong(1, order.getOrderNumber());
                    preparedStatement.setLong(2,orderedItem.getItem().getArticle());
                    preparedStatement.setString(3,orderedItem.getItem().getVariety());
                    preparedStatement.setString(4,orderedItem.getItem().getColor());
                    preparedStatement.setBigDecimal(5, orderedItem.getItem().getPrice());
                    preparedStatement.setLong(6, orderedItem.getOrderedQuantity());
                    int rows = preparedStatement.executeUpdate();
                }
            }
            return order;
        }
        return order;
    }
    
    public Order findAll(Order order, Connection conn) throws SQLException{
       
        String sqlStatement = "SELECT * FROM storedb.ordereditems WHERE orderNumber = ?";
        
        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)) {
            preparedStatement.setLong(1, order.getOrderNumber());
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                long article = resultSet.getLong("article");
                String variety = resultSet.getString("variety");
                String color = resultSet.getString("color");
                BigDecimal fixedPrice = resultSet.getBigDecimal("fixedPrice");
                long orderedQuantity = resultSet.getLong("orderedQuantity");
                
                Good good = new Good(article, variety, color, fixedPrice, 0);
                OrderedItem orderedItem = new OrderedItem(good, fixedPrice, orderedQuantity);
                
                order.add(orderedItem);
            }
        }
        return order;
    }
    
    public Order update (Order order, Connection conn) {
        try {
            Savepoint sp = conn.setSavepoint();
            conn.setAutoCommit(false);
            
            delete (order.getOrderNumber(), conn);
            create (order, conn);
            
            conn.commit();
            conn.setAutoCommit(true);
            conn.releaseSavepoint(sp);
            
        } catch (SQLException ex) {
            System.out.println(ex);
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                }catch (SQLException ex1) {
                    System.err.print("Transaction is being rolled back");
                }
            }
        }
        return order;
    }
    
    public int delete (long orderNumber, Connection conn) throws SQLException{
        String sqlStatement = "DELETE FROM storedb.ordereditems WHERE orderNumber = ?";
        int row;
        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)) {
            preparedStatement.setLong(1, orderNumber);
            row = preparedStatement.executeUpdate();
        }
        return row;
    }
}

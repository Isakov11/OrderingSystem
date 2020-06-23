/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import ru.avalon.java.dev.j120.practice.entity.Good;

public class GoodsDAO {
    private final Connection conn;

    /*public GoodsDAO(String url, String username, String password) throws SQLException {
       conn = DriverManager.getConnection(url, username, password);
    }*/

    public GoodsDAO(Connection conn) {
        this.conn = conn;
    }
    
    public Good create(Good good) throws SQLException{
        long article=0;
        String sqlStatement = "INSERT goods(variety, color, price, instock) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement,Statement.RETURN_GENERATED_KEYS)){
            
            preparedStatement.setString(1, good.getVariety());
            preparedStatement.setString(2, good.getColor());
            preparedStatement.setBigDecimal(3, good.getPrice());
            preparedStatement.setLong(4, good.getInstock());

            int rows = preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
                
            if (rs.next()) {
                article = rs.getLong(1);
            }
            
            return new Good(article, good.getVariety(), good.getColor(), good.getPrice(), good.getInstock());
        }
        catch(IllegalArgumentException | SecurityException | SQLException ex){
            System.out.println("Error! ");
            System.out.println(ex);
            return null;
        }
    }
    
    public ArrayList<Good> findAll() throws SQLException{
        
        ArrayList<Good> goodsArray = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM goods");
            while(resultSet.next()){

                long article = resultSet.getLong("article");
                String variety = resultSet.getString("variety");
                String color = resultSet.getString("color");
                BigDecimal price = resultSet.getBigDecimal("price");
                long instock = resultSet.getLong("instock");

                Good good = new Good(article,variety,color,price,instock);
                goodsArray.add(good);
            }
            
            return goodsArray;
        } catch (SQLException ex) {
            System.out.println(ex);
            
            return goodsArray;
        }
    }
    
    public Good findId(long article) throws SQLException{
        String sqlStatement ="SELECT * FROM goods WHERE article = ?";
        Good good = null;
        
        try( PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)){
            preparedStatement.setLong(1, article);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String variety = resultSet.getString("variety");
                String color = resultSet.getString("color");
                BigDecimal price = resultSet.getBigDecimal("price");
                long instock = resultSet.getLong("instock");

                good = new Good(article,variety,color,price,instock);
            }
            return good;
        } 
    }
    
    public int update(Good good) throws SQLException{
        
        String sqlStatement = "UPDATE goods SET variety = ?, color = ?, price = ?, instock = ? WHERE article= ?";
        
        try( PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)){
            
            preparedStatement.setString(1, good.getVariety());
            preparedStatement.setString(2, good.getColor());
            preparedStatement.setBigDecimal(3, good.getPrice());
            preparedStatement.setLong(4, good.getInstock());
            preparedStatement.setLong(5, good.getArticle());
            
            int row = preparedStatement.executeUpdate();
            
            return row;
            
        } catch (SQLException ex) {
            System.out.println(ex);
            
            return 0;
        }        
    }
    public int update(Collection<Good> goodsColl) throws SQLException{
        
        String sqlStatement = "UPDATE goods SET variety = ?, color = ?, price = ?, instock = ? WHERE article= ?";
        
        try( PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)){
            Savepoint sp = conn.setSavepoint();
            conn.setAutoCommit(false);
            int row =0;
            for(Good good :goodsColl){
                preparedStatement.setString(1, good.getVariety());
                preparedStatement.setString(2, good.getColor());
                preparedStatement.setBigDecimal(3, good.getPrice());
                preparedStatement.setLong(4, good.getInstock());
                preparedStatement.setLong(5, good.getArticle());

                row = preparedStatement.executeUpdate();
            }
            conn.commit();
            conn.setAutoCommit(true);
            conn.releaseSavepoint(sp);
            
            return row;
            
        } catch (SQLException ex) {
            System.out.println(ex);
            try {
                System.err.print("Transaction is being rolled back");
                conn.rollback();
                
            }catch (SQLException ex1) {
                System.err.print("Transaction is being rolled back");
                
            }
        }
        
        return 0;
    }
    
    public int delete(long article) throws SQLException{
        String sqlStatement ="DELETE FROM goods WHERE article = ?";
        try( PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)){
            
            preparedStatement.setLong(1, article);
            int row = preparedStatement.executeUpdate();
            return row;
        } catch (SQLException ex) {
            return 0;
        }
    }
}

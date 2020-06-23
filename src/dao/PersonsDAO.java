/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import ru.avalon.java.dev.j120.practice.entity.Person;

public class PersonsDAO {

    public PersonsDAO() {
    }
    
    public Person create(Person person, Connection conn) throws SQLException{
       
        String sqlStatement = "INSERT persons(name, surname,address,phoneNumber)" +
                "SELECT ?, ?, ?, ? " +
                "WHERE NOT EXISTS (SELECT phoneNumber FROM persons WHERE phoneNumber = ?);";
        
        try (PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)){

            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getSurname());
            preparedStatement.setString(3, person.getDeliveryAddress());
            preparedStatement.setString(4, person.getPhoneNumber());
            preparedStatement.setString(5, person.getPhoneNumber());

            int rows = preparedStatement.executeUpdate();
        }
        catch(IllegalArgumentException | SecurityException | SQLException ex){
            System.out.println("Error! ");
            System.out.println(ex);
            return null;
        }
        return person;
    }
    
    public ArrayList<Person> findAll(Connection conn) throws SQLException{
        
        ArrayList<Person> personsArray = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM persons");
            while(resultSet.next()){

                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phoneNumber");
                Person person = new Person(name,surname,address,phoneNumber);
                personsArray.add(person);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            
            return personsArray;
        }
        
        return personsArray;
    }
    
    public Person findId(String phoneNumber, Connection conn) throws SQLException{
            
        String sqlStatement ="SELECT * FROM persons WHERE phoneNumber = ?";
        
        try( PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)){
            
            preparedStatement.setString(1, phoneNumber);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String address = resultSet.getString("address");
                Person person = new Person(name,surname,address,phoneNumber);
                return person;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            
            return null;
        }
        
        return null;
    }
    
    public int update(Person person, Connection conn) throws SQLException{
        
        String sqlStatement = "UPDATE persons SET name = ?, surname = ?, address = ? WHERE phoneNumber= ?";
        
        try( PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)){
            
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getSurname());
            preparedStatement.setString(3, person.getDeliveryAddress());
            preparedStatement.setString(4, person.getPhoneNumber());
            
            int row = preparedStatement.executeUpdate();
            
            return row;
            
        } catch (SQLException ex) {
            System.out.println(ex);
            
            return 0;
        }        
    }
    
    public int delete(String phoneNumber, Connection conn) throws SQLException{
        
        String sqlStatement ="DELETE FROM goods WHERE phoneNumber = ?";
        
        try( PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement)){
            
            preparedStatement.setString(1, phoneNumber);
            
            int row = preparedStatement.executeUpdate();
            
            return row;
            
        } catch (SQLException ex) {
            
            return 0;
        }
    }
}

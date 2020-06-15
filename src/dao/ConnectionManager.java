/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {
    private String url;
    private String username;
    private String password;
    private Connection connection;
    
    public ConnectionManager(String url, String username, String password)  {
        try {
            this.url = url;
            this.username = username;
            this.password = password;
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            
        }
    }

    public Connection getConnection() {
        return connection;
    }
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            
        }
    }
}

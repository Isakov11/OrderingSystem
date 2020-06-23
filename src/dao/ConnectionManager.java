/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private String url, username, password;
    
    public ConnectionManager(String url, String username, String password) throws SQLException  {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public final Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    public void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private int cpds;
    
    public ConnectionManager(String url, String username, String password) throws SQLException  {
        
        
    }

    public Connection getConnection() throws SQLException {
        return null;
    }
    public void closeConnection(Connection conn) {
        
    }
}

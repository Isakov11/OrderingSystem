/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static com.mchange.v1.db.sql.ConnectionUtils.attemptClose;
import java.sql.Connection;
import java.sql.SQLException;
import com.mchange.v2.c3p0.*;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {
    private Connection connection;
    private ComboPooledDataSource cpds;
    
    public ConnectionManager(String url, String username, String password)  {
        try {
            /*this.url = url;
            this.username = username;
            this.password = password;
            connection = DriverManager.getConnection(url, username, password);*/
            
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("com.mysql.cj.jdbc.Driver"); //loads the jdbc driver            
            cpds.setJdbcUrl(url);
            cpds.setUser(username);                                  
            cpds.setPassword(password);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        try {
            return cpds.getConnection();
        } catch (SQLException ex) {
            
        }
        return null;
    }
    public void closeConnection(Connection conn) {
        attemptClose(conn);
    }
}

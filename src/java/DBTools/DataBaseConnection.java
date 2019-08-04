/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michail Sitmalidis
 */
public class DataBaseConnection {

    public static Connection getInitConnection() throws SQLException {
        Connection connection = null;
        try {

            String driver = "com.mysql.cj.jdbc.Driver";
            /*
            String url = "jdbc:mysql://ppp-mirror-db.ciwrtxzcjee9.eu-west-1.rds.amazonaws.com/pppdb?useSSL=false";
            String username = "boxm16";
            String password = "athina2004";
             */
            String url = "jdbc:mysql://remotemysql.com/h0u4Z7iqi8?useSSL=false";
            String username = "h0u4Z7iqi8";
            String password = "Z5dTwqvCQd";

            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connection created at: " + LocalDateTime.now());
        return connection;
    }

}

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MISHA
 */
public class SingletonDataBaseConnection {

    private static DataBaseConnection DBCInstance;
    private static Connection connection;

    private SingletonDataBaseConnection() {
        //keeping it private
    }

    public static DataBaseConnection getDBCInstance() {
        if (DBCInstance == null) {
            DBCInstance = new DataBaseConnection();
        }
        return DBCInstance;
    }

    public Connection getConnection() {

        if (connection == null) {
            try {

                System.out.println("----Creating a connection----");

                String driver = "com.mysql.cj.jdbc.Driver";
                String url = "jdbc:mysql://remotemysql.com/h0u4Z7iqi8?useSSL=false";
                String username = "h0u4Z7iqi8";
                String password = "Z5dTwqvCQd";

                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);

            } catch (ClassNotFoundException | SQLException ex) {

                Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);

            }

        }
        System.out.println("Connection created at: " + LocalDateTime.now());
        return connection;
    }

}

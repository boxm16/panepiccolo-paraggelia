/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBTools;

import Models.Customer;
import Models.CustomersRatingTable;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author boxm1
 */
@Repository
public class UserDao {

    public User checkUserByUsername(String username) {
        User user = new User();
        try {
            try (Connection connection = DataBaseConnection.getInitConnection(); Statement statement = connection.createStatement()) {
                String sql;
                sql = "SELECT * FROM user WHERE username='" + username + "'";
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {

                    int user_id = rs.getInt("user_id");
                    String official_name = rs.getString("official_name");
                    String second_name = rs.getString("second_name");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    String status = rs.getString("status");
                    int rating = rs.getInt("rating");
                    int customer_code = rs.getInt("customer_code");

                    user.setUser_id(user_id);
                    user.setOfficial_name(official_name);
                    user.setSecond_name(second_name);
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setRole(role);
                    user.setStatus(status);
                    user.setRating(rating);
                    user.setCustomer_code(customer_code);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public LinkedHashMap<Integer, Customer> getCustomers() {
        LinkedHashMap<Integer, Customer> customersMap = new LinkedHashMap<>();

        try (Connection connection = DataBaseConnection.getInitConnection(); Statement statement = connection.createStatement()) {

            String SQL = "SELECT * FROM user WHERE role='customer' ORDER BY rating  ";

            ResultSet rs = statement.executeQuery(SQL);

            while (rs.next()) {

                int user_id = rs.getInt("user_id");
                String official_name = rs.getString("official_name");
                String second_name = rs.getString("second_name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String status = rs.getString("status");
                int rating = rs.getInt("rating");
                int customer_code = rs.getInt("customer_code");

                User user = new User();
                user.setUser_id(user_id);
                user.setOfficial_name(official_name);
                user.setSecond_name(second_name);
                user.setUsername(username);
                user.setPassword(password);
                user.setRole(role);
                user.setStatus(status);
                user.setRating(rating);
                user.setCustomer_code(customer_code);

                Customer customer = new Customer();
                customer.setUser(user);
                customersMap.put(user_id, customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return customersMap;

    }

    public User getUserByID(int user_id) {

        User user = new User();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {
            String sql = "SELECT * FROM user WHERE user_id='" + user_id + "'";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                String official_name = rs.getString("official_name");
                String second_name = rs.getString("second_name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String status = rs.getString("status");
                int rating = rs.getInt("rating");
                int customer_code = rs.getInt("customer_code");

                user.setUser_id(user_id);
                user.setOfficial_name(official_name);
                user.setSecond_name(second_name);
                user.setUsername(username);
                user.setPassword(password);
                user.setRole(role);
                user.setStatus(status);
                user.setRating(rating);
                user.setCustomer_code(customer_code);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;

    }

    public void updateUsersRating(CustomersRatingTable customersRatingTable) {
        String updateUsersRatingSQL = "UPDATE user SET rating=? WHERE username=?";

        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement insertOrderItemsPrepStatement = connection.prepareStatement(updateUsersRatingSQL)) {
            for (int a = 0; a < customersRatingTable.getCustomersRatingTable().size(); a++) {
                //OO,this is bad, realy bad, really really bad
                insertOrderItemsPrepStatement.setInt(1, customersRatingTable.getCustomersRatingTable().get(a).getRating());
                insertOrderItemsPrepStatement.setString(2, customersRatingTable.getCustomersRatingTable().get(a).getUsername());
                insertOrderItemsPrepStatement.addBatch();
            }
            insertOrderItemsPrepStatement.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

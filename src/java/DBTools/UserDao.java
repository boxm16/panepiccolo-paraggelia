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
import java.util.ArrayList;
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

    public LinkedHashMap<Integer, Customer> getActiveCustomers() {
        LinkedHashMap<Integer, Customer> customersMap = new LinkedHashMap<>();

        try (Connection connection = DataBaseConnection.getInitConnection(); Statement statement = connection.createStatement()) {

            String SQL = "SELECT * FROM user WHERE role='customer' and status='active' ORDER BY rating  ";

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

    public LinkedHashMap<Integer, Customer> getCustomers() {
        LinkedHashMap<Integer, Customer> customersMap = new LinkedHashMap<>();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement()) {

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

    public void inserNewCustomer(User user) {
        String inserUserSQL = "INSERT INTO user (official_name, second_name, username, password, role, rating, status, customer_code) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement insertUser = connection.prepareStatement(inserUserSQL)) {
            int maxRating;
            try (Statement statement = connection.createStatement()) {
                String SQL = "SELECT MAX(rating) AS MAX_R FROM user";
                ResultSet rs = statement.executeQuery(SQL);
                maxRating = 0;
                while (rs.next()) {
                    maxRating = rs.getInt("MAX_R") + 1;
                }
                rs.close();
            }

            insertUser.setString(1, user.getOfficial_name());
            insertUser.setString(2, user.getSecond_name());
            insertUser.setString(3, user.getUsername());
            insertUser.setString(4, user.getPassword());
            insertUser.setString(5, "customer");
            insertUser.setInt(6, maxRating);
            insertUser.setString(7, "active");
            insertUser.setInt(8, 0);
            insertUser.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deactivateUserByUserID(int user_id) {

        String deactivateUserSQL = "UPDATE user SET status='deactivated', rating='0' WHERE user_id=?";
        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement deactivateUser = connection.prepareStatement(deactivateUserSQL);) {

            deactivateUser.setInt(1, user_id);

            deactivateUser.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void reactivateUserByUserID(int user_id) {
        String deactivateUserSQL = "UPDATE user SET status='active' WHERE user_id=?";
        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement deleteUser = connection.prepareStatement(deactivateUserSQL);) {

            deleteUser.setInt(1, user_id);

            deleteUser.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateUserCustomer(User user) {
        String editUserSQL = "UPDATE user SET official_name=?, second_name=?, username=?, password=? WHERE user_id=?";
        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement editUser = connection.prepareStatement(editUserSQL);) {

            editUser.setString(1, user.getOfficial_name());
            editUser.setString(2, user.getSecond_name());
            editUser.setString(3, user.getUsername());
            editUser.setString(4, user.getPassword());
            editUser.setInt(5, user.getUser_id());
            editUser.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<User> getObservers() {

        ArrayList<User> observers = new ArrayList<>();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement()) {

            String SQL = "SELECT * FROM user WHERE role='observer' ORDER BY rating  ";

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

                observers.add(user);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return observers;
    }

    public void inserNewObserver(User user) {
        String inserUserSQL = "INSERT INTO user (official_name, second_name, username, password, role, rating, status, customer_code) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement insertUser = connection.prepareStatement(inserUserSQL)) {
     
         

            insertUser.setString(1, user.getOfficial_name());
            insertUser.setString(2, user.getOfficial_name());
            insertUser.setString(3, user.getUsername());
            insertUser.setString(4, user.getPassword());
            insertUser.setString(5, "observer");
            insertUser.setInt(6, 0);
            insertUser.setString(7, "active");
            insertUser.setInt(8, 0);
            insertUser.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteUserByUserId(int user_id) {
        
         String deactivateUserSQL = "DELETE FROM user WHERE user_id=?";
        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement deleteProduct = connection.prepareStatement(deactivateUserSQL);) {
            
            deleteProduct.setInt(1, user_id);
            
            deleteProduct.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

}

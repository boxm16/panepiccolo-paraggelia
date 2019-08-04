/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBTools;

import Models.FavoriteProduct;
import Models.Order;
import Models.OrderItem;
import Models.OrderTemplate;
import Models.Prefered_Item;
import Models.Product;
import Models.TemplateItem;
import Tools.OrderMixer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michail Sitmalidis
 */
@Repository
public class OrderTemplateDao {

    public List<OrderItem> getOrderTemplate(int user_id, String day) {

        List<OrderItem> orderItems = new ArrayList<>();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement()) {

            if (day.equals("NoDay")) {
                String sql = "SELECT template_item.product_id, selling_name, baking_name  FROM template_item INNER JOIN product ON product.product_id=template_item.product_id WHERE user_id=" + user_id + "";
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {

                    int product_id = rs.getInt("product_id");
                    String selling_name = rs.getString("selling_name");
                    String baking_name = rs.getString("baking_name");

                    Product product = new Product();
                    product.setProduct_id(product_id);
                    product.setSelling_name(selling_name);
                    product.setBaking_name(baking_name);

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(0);
                    orderItems.add(orderItem);

                }
            } else {
                String sql = "SELECT template_item.product_id, selling_name, baking_name,  " + day + " FROM template_item INNER JOIN product ON product.product_id=template_item.product_id WHERE user_id=" + user_id + "";
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {
                    int product_id = rs.getInt("product_id");
                    String selling_name = rs.getString("selling_name");
                    String baking_name = rs.getString("baking_name");
                    int quantity = rs.getInt(day);

                    Product product = new Product();
                    product.setProduct_id(product_id);
                    product.setSelling_name(selling_name);
                    product.setBaking_name(baking_name);

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(quantity);
                    orderItems.add(orderItem);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orderItems;
    }

    public boolean autogeneratorActivated(int user_id) {
        boolean activated = false;

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM  template_item WHERE user_id=" + user_id;
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                activated = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return activated;
    }

    public void activateAutogenerator(int user_id, List<FavoriteProduct> favoriteProductsList) {

        try (Connection connection = DataBaseConnection.getInitConnection();) {
            String insertOrderItemsSQL = "INSERT INTO  template_item(user_id, product_id, day_1, day_2, day_3, day_4, day_5, day_6, day_7) "
                    + "VALUES(?,?,?,?,?,?,?,?,?);";

            try (PreparedStatement insertOrderItemsStatement = connection.prepareStatement(insertOrderItemsSQL)) {
                for (FavoriteProduct favorite_product : favoriteProductsList) {
                    insertOrderItemsStatement.setInt(1, user_id);
                    insertOrderItemsStatement.setInt(2, favorite_product.getProduct().getProduct_id());

                    insertOrderItemsStatement.setInt(3, 0);

                    insertOrderItemsStatement.setInt(4, 0);

                    insertOrderItemsStatement.setInt(5, 0);

                    insertOrderItemsStatement.setInt(6, 0);

                    insertOrderItemsStatement.setInt(7, 0);

                    insertOrderItemsStatement.setInt(8, 0);

                    insertOrderItemsStatement.setInt(9, 0);
                    insertOrderItemsStatement.addBatch();
                }
                insertOrderItemsStatement.executeBatch();
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deactivateAutogenerator(int user_id) {
        try (Connection connection = DataBaseConnection.getInitConnection();) {
            String deleteAutogenaratedItems = "DELETE FROM  template_item WHERE user_id=" + user_id;

            try (PreparedStatement deleteAutogenaratedItemsStatement = connection.prepareStatement(deleteAutogenaratedItems)) {

                deleteAutogenaratedItemsStatement.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderTemplateDao.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void updateOrderTemplate(OrderTemplate orderTemplate) {

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {

            connection.setAutoCommit(false);

            for (TemplateItem templateItem : orderTemplate.getTemplateItems()) {
                String updateTemplate_day_1 = "UPDATE template_item SET day_1 =" + templateItem.getDay_1() + " WHERE user_id=" + orderTemplate.getUser_id() + " and product_id=" + templateItem.getProduct().getProduct_id() + "";
                statement.addBatch(updateTemplate_day_1);
                String updateTemplate_day_2 = "UPDATE template_item SET day_2 =" + templateItem.getDay_2() + " WHERE user_id=" + orderTemplate.getUser_id() + " and product_id=" + templateItem.getProduct().getProduct_id() + "";
                statement.addBatch(updateTemplate_day_2);
                String updateTemplate_day_3 = "UPDATE template_item SET day_3 =" + templateItem.getDay_3() + " WHERE user_id=" + orderTemplate.getUser_id() + " and product_id=" + templateItem.getProduct().getProduct_id() + "";
                statement.addBatch(updateTemplate_day_3);
                String updateTemplate_day_4 = "UPDATE template_item SET day_4 =" + templateItem.getDay_4() + " WHERE user_id=" + orderTemplate.getUser_id() + " and product_id=" + templateItem.getProduct().getProduct_id() + "";
                statement.addBatch(updateTemplate_day_4);
                String updateTemplate_day_5 = "UPDATE template_item SET day_5 =" + templateItem.getDay_5() + " WHERE user_id=" + orderTemplate.getUser_id() + " and product_id=" + templateItem.getProduct().getProduct_id() + "";
                statement.addBatch(updateTemplate_day_5);
                String updateTemplate_day_6 = "UPDATE template_item SET day_6 =" + templateItem.getDay_6() + " WHERE user_id=" + orderTemplate.getUser_id() + " and product_id=" + templateItem.getProduct().getProduct_id() + "";
                statement.addBatch(updateTemplate_day_6);
                String updateTemplate_day_7 = "UPDATE template_item SET day_7 =" + templateItem.getDay_7() + " WHERE user_id=" + orderTemplate.getUser_id() + " and product_id=" + templateItem.getProduct().getProduct_id() + "";
                statement.addBatch(updateTemplate_day_7);
            }

            if (orderTemplate.isDay_1()) {

            }
            if (orderTemplate.isDay_2()) {

            }
            if (orderTemplate.isDay_3()) {

            }
            if (orderTemplate.isDay_4()) {

            }
            if (orderTemplate.isDay_5()) {

            }
            if (orderTemplate.isDay_6()) {

            }
            if (orderTemplate.isDay_7()) {

            }
            statement.executeBatch();
            connection.commit();

        } catch (SQLException ex) {
            Logger.getLogger(OrderTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<Integer, Order> getAutogeneratedOrdersListForDay(String day) {
        List<TemplateItem> templateItemsList = new ArrayList<>();

        Map<Integer, Order> ordersMap = null;

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {
            String SQL = "SELECT user_id, product_id, " + day + " FROM template_item;";

            ResultSet rs = statement.executeQuery(SQL);

            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                int product_id = rs.getInt("product_id");
                int day_quantity = rs.getInt(day);

                TemplateItem orderTemplateItem = new TemplateItem();
                Product product = new Product();
                product.setProduct_id(product_id);
                orderTemplateItem.setProduct(product);
                orderTemplateItem.setUser_id(user_id);

                orderTemplateItem.setDay_quantity(day_quantity);
                templateItemsList.add(orderTemplateItem);

            }

            ordersMap = OrderMixer.getOrdersMapFromOrderTemplateItems(templateItemsList);

        } catch (SQLException ex) {
            Logger.getLogger(OrderTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordersMap;
    }

    public List<TemplateItem> getOrderTemplate(int user_id) {

        List<TemplateItem> templateItemsList = new ArrayList<>();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement()) {

            String sql = "SELECT template_item.product_id, selling_name, baking_name, day_1, day_2, day_3, day_4, day_5, day_6, day_7  "
                    + "FROM template_item "
                    + "INNER JOIN product ON product.product_id=template_item.product_id "
                    + "WHERE user_id=" + user_id + ";";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                int product_id = rs.getInt("product_id");
                String selling_name = rs.getString("selling_name");
                String baking_name = rs.getString("baking_name");
                int day_1 = rs.getInt("day_1");
                int day_2 = rs.getInt("day_2");
                int day_3 = rs.getInt("day_3");
                int day_4 = rs.getInt("day_4");
                int day_5 = rs.getInt("day_5");
                int day_6 = rs.getInt("day_6");
                int day_7 = rs.getInt("day_7");

                Product product = new Product();
                product.setProduct_id(product_id);
                product.setSelling_name(selling_name);
                product.setBaking_name(baking_name);

                TemplateItem templateItem = new TemplateItem();
                templateItem.setProduct(product);
                templateItem.setDay_1(day_1);
                templateItem.setDay_2(day_2);
                templateItem.setDay_3(day_3);
                templateItem.setDay_4(day_4);
                templateItem.setDay_5(day_5);
                templateItem.setDay_6(day_6);
                templateItem.setDay_7(day_7);

                templateItemsList.add(templateItem);

            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return templateItemsList;

    }

    public void addFavoriteProductToOrderTemplate(FavoriteProduct favoriteProduct) {
        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {

            String addFavoriteProductToOrderTemplate = "INSERT INTO  template_item(user_id, product_id, day_1, day_2, day_3, day_4, day_5, day_6, day_7) "
                    + "VALUES(" + favoriteProduct.getUser().getUser_id() + "," + favoriteProduct.getProduct().getProduct_id() + ",0,0,0,0,0,0,0);";
            statement.execute(addFavoriteProductToOrderTemplate);

        } catch (SQLException ex) {
            Logger.getLogger(OrderTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteTemplateItem(int user_id, int product_id) {
        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM  template_item WHERE user_id=? AND product_id=?;");) {
            prepStatement.setInt(1, user_id);
            prepStatement.setInt(2, product_id);

            prepStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

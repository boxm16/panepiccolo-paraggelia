/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBTools;

import Models.Order;
import Models.OrderItem;
import Models.Product;
import Models.ProductOrderer;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author boxm1
 */
@Repository
public class OrderDao {

    @Autowired
    private ProductDao productDao;

    public LinkedHashMap<Integer, Order> getActiveOrdersMap() {

        LinkedHashMap<Integer, Order> ordersMap = new LinkedHashMap<>();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {

            String sql = "SELECT order_item.order_id, order_item.product_id, order_item.quantity, pp_order.orderer, pp_order.creation_time, pp_order.due_day, product.selling_name, product.selling_unit, product.baking_name, product.baking_unit, product.product_code\n"
                    + " FROM order_item  \n"
                    + "INNER JOIN pp_order on order_item.order_id=pp_order.order_id \n"
                    + "INNER JOIN product on  order_item.product_id=product.product_id\n"
                    + "WHERE pp_order.status='active';";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                int order_id = rs.getInt("order_id");
                int orderer = rs.getInt("orderer");
                String creation_date = rs.getString("creation_time");
                String due_day = rs.getString("due_day");

                int product_id = rs.getInt("product_id");
                String selling_name = rs.getString("selling_name");
                Double selling_unit = rs.getDouble("selling_unit");
                String baking_name = rs.getString("baking_name");
                Double baking_unit = rs.getDouble("baking_unit");
                int product_code = rs.getInt("product_code");

                int quantity = rs.getInt("quantity");

                Product product = new Product();
                product.setProduct_id(product_id);
                product.setSelling_name(selling_name);
                product.setSelling_unit(selling_unit);
                product.setBaking_name(baking_name);
                product.setBaking_unit(baking_unit);
                product.setProduct_code(product_code);

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(quantity);
                if (ordersMap.containsKey(order_id)) {

                    ordersMap.get(order_id).getOrderItems().add(orderItem);

                } else {
                    Order order = new Order();
                    order.setOrder_id(order_id);
                    order.setOrderer(orderer);
                    order.setCreation_time(creation_date);
                    order.setDue_day(due_day);
                    order.getOrderItems().add(orderItem);
                    ordersMap.put(order_id, order);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordersMap;
    }

    public boolean checkOrder(Order order) {
        boolean exist = false;

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM pp_order WHERE orderer=" + order.getOrderer() + " AND status='active' AND due_day='" + order.getDue_day() + "'";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                exist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exist;
    }

    public int insertOrder(Order order) {
        int increment_numb = 0;
        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO  pp_order(orderer, status, creation_time, due_day, operator, operation_time) "
                        + "VALUES(?,?,?,?,?,?);");) {
            int a = order.getOrderer();//here goes orderer_id
            String b = "active";
            String nowTime = getDateTime();

            int operator = 1;//here goes operator id
            prepStatement.setInt(1, a);
            prepStatement.setString(2, b);
            prepStatement.setString(3, nowTime);
            prepStatement.setString(4, order.getDue_day());
            prepStatement.setInt(5, operator);
            prepStatement.setString(6, nowTime);
            prepStatement.execute();

            try (Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT MAX(order_id) AS AUTO_INCREMENT FROM pp_order ")) {
                while (rs.next()) {
                    increment_numb = rs.getInt("AUTO_INCREMENT");//next order id
                }
            }

            String insertOrderItemsSQL = "INSERT INTO order_item(order_id, product_id, quantity) "
                    + "VALUES (?,?,?)";
            try (PreparedStatement insertOrderItemsStatement = connection.prepareStatement(insertOrderItemsSQL)) {
                for (OrderItem orderItem : order.getOrderItems()) {

                    insertOrderItemsStatement.setInt(1, increment_numb);
                    insertOrderItemsStatement.setInt(2, orderItem.getProduct().getProduct_id());
                    insertOrderItemsStatement.setDouble(3, orderItem.getQuantity());
                    insertOrderItemsStatement.addBatch();
                }
                insertOrderItemsStatement.executeBatch();
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return increment_numb;
    }

    public Order getOrderByID(int order_id) {
        Order order = new Order();
        order.setOrder_id(order_id);

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement()) {

            String sql = "SELECT * FROM pp_order WHERE order_id='" + order_id + "'";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                int orderer = rs.getInt("orderer");
                String status = rs.getString("status");
                String creation_time = rs.getString("creation_time");
                String due_day = rs.getString("due_day");
                int operator = rs.getInt("operator");
                String operation_time = rs.getString("operation_time");
                List<OrderItem> orderItems = getOrderItemsByOrderId(order_id);
//really man, really, ???????? one connection for each item. it`s not even funny. change it
                order.setOrderer(orderer);
                order.setStatus(status);
                order.setCreation_time(creation_time);
                order.setDue_day(due_day);
                order.setOperator(operator);
                order.setOperation_time(operation_time);
                order.setOrderItems(orderItems);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order;
    }

    private List<OrderItem> getOrderItemsByOrderId(int order_id) {
        List<OrderItem> orderItems = new ArrayList<>();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {
            String sql = "SELECT product_id, quantity FROM order_item WHERE order_id='" + order_id + "'";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                OrderItem orderItem = new OrderItem();

                int product_id = rs.getInt("product_id");
                int quantitiy = rs.getInt("quantity");


                Product product = productDao.getProductByID(product_id);
                orderItem.setProduct(product);
                orderItem.setQuantity(quantitiy);
                orderItems.add(orderItem);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderItems;
    }

    public void modifyOrder(Order order, int newOrderId, HttpSession session) {
        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement prepStatement = connection.prepareStatement("UPDATE pp_order SET status = ?, operator= ?, operation_time= ?  WHERE order_id = ?")) {
            String status = "replaced by order number: " + newOrderId;
            prepStatement.setString(1, status);

            System.out.println("user from session at ModifyOrder in orderDao: " + session.getAttribute("user"));

            User user = (User) session.getAttribute("user");//risky point
            prepStatement.setInt(2, user.getUser_id());//here goes operator id from session
            String nowTime = getDateTime();
            prepStatement.setString(3, nowTime);
            prepStatement.setInt(4, order.getOrder_id());
            prepStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getDateTime() {
        ZoneId athensZone = ZoneId.of("Europe/Athens");
        LocalDateTime now = LocalDateTime.now(athensZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);
        return formatDateTime;
    }

    public void cancelOrder(Order order, HttpSession session) {
        try (Connection connection = DataBaseConnection.getInitConnection()) {
            String query = "UPDATE pp_order SET status = ?, operator=?, operation_time=? WHERE order_id = ?";
            try (PreparedStatement prepStatement = connection.prepareStatement(query)) {
                String status = "canceled";
                prepStatement.setString(1, status);

                //risky  point man
                System.out.println("user from session at cancelOrder in orderDao: " + session.getAttribute("user"));

                User user = (User) session.getAttribute("user");
                prepStatement.setInt(2, user.getUser_id());
                String nowTime = getDateTime();
                prepStatement.setString(3, nowTime);
                prepStatement.setInt(4, order.getOrder_id());
                prepStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void completeOrders() {
        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {
            String query = "UPDATE pp_order SET status = 'completed' WHERE status = 'locked'";
            statement.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LinkedHashMap<Integer, Order> getLockedOrdersMap() {

        LinkedHashMap<Integer, Order> ordersMap = new LinkedHashMap<>();
        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {

            String sql;

            sql = "SELECT order_item.order_id, order_item.product_id, order_item.quantity, pp_order.orderer, pp_order.creation_time, pp_order.due_day, product.selling_name, product.selling_unit, product.baking_name, product.baking_unit, product.product_code\n"
                    + " FROM order_item  \n"
                    + "INNER JOIN pp_order on order_item.order_id=pp_order.order_id \n"
                    + "INNER JOIN product on  order_item.product_id=product.product_id\n"
                    + "WHERE pp_order.status='locked';";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                int order_id = rs.getInt("order_id");
                int orderer = rs.getInt("orderer");
                String creation_date = rs.getString("creation_time");
                String due_day = rs.getString("due_day");

                int product_id = rs.getInt("product_id");
                String selling_name = rs.getString("selling_name");
                Double selling_unit = rs.getDouble("selling_unit");
                String baking_name = rs.getString("baking_name");
                Double baking_unit = rs.getDouble("baking_unit");
                int product_code = rs.getInt("product_code");

                int quantity = rs.getInt("quantity");

                Product product = new Product();
                product.setProduct_id(product_id);
                product.setSelling_name(selling_name);
                product.setSelling_unit(selling_unit);
                product.setBaking_name(baking_name);
                product.setBaking_unit(baking_unit);
                product.setProduct_code(product_code);

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(quantity);
                if (ordersMap.containsKey(order_id)) {

                    ordersMap.get(order_id).getOrderItems().add(orderItem);

                } else {
                    Order order = new Order();
                    order.setOrder_id(order_id);
                    order.setOrderer(orderer);
                    order.setCreation_time(creation_date);
                    order.setDue_day(due_day);
                    order.getOrderItems().add(orderItem);
                    ordersMap.put(order_id, order);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordersMap;
    }

    public List<OrderItem> getOrderedItemsBakingUnitSummary_ActiveOrders() {
        List<OrderItem> orderedItemsSummaryList = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {

            String sql = "SELECT order_item.product_id, product.baking_name, SUM(quantity*product.baking_unit) as totalSUM\n"
                    + " FROM order_item INNER JOIN pp_order ON order_item.order_id=pp_order.order_id \n"
                    + " INNER JOIN product ON order_item.product_id=product.product_id \n"
                    + " WHERE pp_order.status='active' Group by product_id ORDER BY baking_name;";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int product_id = rs.getInt("product_id");
                String baking_name = rs.getString("baking_name");
                int quantity = (int) rs.getDouble("totalSUM");

                Product product = new Product();
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.getProduct().setProduct_id(product_id);
                orderItem.getProduct().setBaking_name(baking_name);
                orderItem.setQuantity(quantity);

                orderedItemsSummaryList.add(orderItem);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orderedItemsSummaryList;
    }

    public List<OrderItem> getOrderedItemsBakingUnitSummary_LockedOrders() {
        List<OrderItem> orderedItemsSummaryList = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {
            String sql = "SELECT order_item.product_id, product.baking_name, SUM(quantity*product.baking_unit) as totalSUM\n"
                    + " FROM order_item INNER JOIN pp_order ON order_item.order_id=pp_order.order_id \n"
                    + " INNER JOIN product ON order_item.product_id=product.product_id \n"
                    + " WHERE pp_order.status='locked' Group by product_id ORDER BY baking_name;";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int product_id = rs.getInt("product_id");
                String baking_name = rs.getString("baking_name");
                int quantity = (int) rs.getDouble("totalSUM");

                Product product = new Product();
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.getProduct().setProduct_id(product_id);
                orderItem.getProduct().setBaking_name(baking_name);
                orderItem.setQuantity(quantity);

                orderedItemsSummaryList.add(orderItem);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orderedItemsSummaryList;
    }

    public List<ProductOrderer> getProductOrderersList_ActiveOrders(int product_id) {
        List<ProductOrderer> productOrdererList = new ArrayList<>();
        List<OrderItem> orderedItemsSummaryList = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {
            String sql = "SELECT pp_order.order_id, pp_order.orderer, user.second_name, product.baking_name, (order_item.quantity*product.baking_unit) as baking_quantity "
                    + "FROM pp_order  "
                    + "INNER JOIN order_item ON pp_order.order_id=order_item.order_id  "
                    + "INNER JOIN user ON pp_order.orderer=user.user_id "
                    + "INNER JOIN product ON order_item.product_id=product.product_id WHERE pp_order.status='active' and order_item.product_id=" + product_id + ";";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int order_id = rs.getInt("order_id");
                int orderer_id = rs.getInt("orderer");
                String second_name = rs.getString("second_name");

                String baking_name = rs.getString("baking_name");
                int quantity = rs.getInt("baking_quantity");

                Order order = new Order();
                order.setOrder_id(order_id);

                User user = new User();
                user.setUser_id(orderer_id);
                user.setSecond_name(second_name);

                Product product = new Product();
                product.setBaking_name(baking_name);

                ProductOrderer productOrderer = new ProductOrderer();
                productOrderer.setUser(user);
                productOrderer.setOrder(order);
                productOrderer.setProduct(product);
                productOrderer.setQuantity(quantity);

                productOrdererList.add(productOrderer);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return productOrdererList;
    }

    public List<ProductOrderer> getProductOrderersList_LockedOrders(int product_id) {
        List<ProductOrderer> productOrdererList = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement()) {

            String sql = "SELECT pp_order.order_id, pp_order.orderer, user.second_name, product.baking_name, (order_item.quantity*product.baking_unit) as baking_quantity"
                    + " FROM pp_order "
                    + "INNER JOIN order_item ON pp_order.order_id=order_item.order_id  "
                    + "INNER JOIN user ON pp_order.orderer=user.user_id "
                    + "INNER JOIN product ON order_item.product_id=product.product_id "
                    + "WHERE pp_order.status='locked' and order_item.product_id=" + product_id + ";";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int order_id = rs.getInt("order_id");
                int orderer_id = rs.getInt("orderer");
                String second_name = rs.getString("second_name");
                String baking_name = rs.getString("baking_name");
                int quantity = rs.getInt("baking_quantity");

                Order order = new Order();
                order.setOrder_id(order_id);

                User user = new User();
                user.setUser_id(orderer_id);
                user.setSecond_name(second_name);

                Product product = new Product();
                product.setBaking_name(baking_name);

                ProductOrderer productOrderer = new ProductOrderer();
                productOrderer.setUser(user);
                productOrderer.setOrder(order);
                productOrderer.setProduct(product);
                productOrderer.setQuantity(quantity);

                productOrdererList.add(productOrderer);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return productOrdererList;
    }

    public Order getMyLastOrder(int user_id) {
        int myLastOrder_id = 0;
        Order order = new Order();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement()) {

            String sql_1 = "SELECT order_id FROM pp_order WHERE orderer="+user_id+" and status='completed' ORDER BY order_id DESC LIMIT 1;";
            ResultSet rs_1 = statement.executeQuery(sql_1);
            while (rs_1.next()) {
                myLastOrder_id = rs_1.getInt("order_id");
            }

            String sql_2 = "SELECT * FROM pp_order WHERE order_id='" + myLastOrder_id + "'";
            ResultSet rs_2 = statement.executeQuery(sql_2);

            while (rs_2.next()) {
                order.setOrder_id(myLastOrder_id);
                int orderer = rs_2.getInt("orderer");
                String status = rs_2.getString("status");
                String creation_time = rs_2.getString("creation_time");
                String due_day = rs_2.getString("due_day");
                int operator = rs_2.getInt("operator");
                String operation_time = rs_2.getString("operation_time");
                List<OrderItem> orderItems = getOrderItemsByOrderId(myLastOrder_id);

                order.setOrderer(orderer);
                order.setStatus(status);
                order.setCreation_time(creation_time);
                order.setDue_day(due_day);
                order.setOperator(operator);
                order.setOperation_time(operation_time);
                order.setOrderItems(orderItems);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order;

    }
    
    
     public LinkedHashMap<Integer, Order> getActiveOrdersMapByUserID(int userID) {

        LinkedHashMap<Integer, Order> ordersMap = new LinkedHashMap<>();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {

            String sql = "SELECT order_item.order_id, order_item.product_id, order_item.quantity, pp_order.orderer, pp_order.creation_time, pp_order.due_day, product.selling_name, product.selling_unit, product.baking_name, product.baking_unit, product.product_code\n"
                    + " FROM order_item  \n"
                    + "INNER JOIN pp_order on order_item.order_id=pp_order.order_id \n"
                    + "INNER JOIN product on  order_item.product_id=product.product_id\n"
                    + "WHERE pp_order.status='active'"
                    + "and pp_order.orderer="+userID+";";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                int order_id = rs.getInt("order_id");
                int orderer = rs.getInt("orderer");
                String creation_date = rs.getString("creation_time");
                String due_day = rs.getString("due_day");

                int product_id = rs.getInt("product_id");
                String selling_name = rs.getString("selling_name");
                Double selling_unit = rs.getDouble("selling_unit");
                String baking_name = rs.getString("baking_name");
                Double baking_unit = rs.getDouble("baking_unit");
                int product_code = rs.getInt("product_code");

                int quantity = rs.getInt("quantity");

                Product product = new Product();
                product.setProduct_id(product_id);
                product.setSelling_name(selling_name);
                product.setSelling_unit(selling_unit);
                product.setBaking_name(baking_name);
                product.setBaking_unit(baking_unit);
                product.setProduct_code(product_code);

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(quantity);
                if (ordersMap.containsKey(order_id)) {

                    ordersMap.get(order_id).getOrderItems().add(orderItem);

                } else {
                    Order order = new Order();
                    order.setOrder_id(order_id);
                    order.setOrderer(orderer);
                    order.setCreation_time(creation_date);
                    order.setDue_day(due_day);
                    order.getOrderItems().add(orderItem);
                    ordersMap.put(order_id, order);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordersMap;
    }
}

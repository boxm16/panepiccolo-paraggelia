/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBTools;

import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michail Sitmalidis
 */
@Repository
public class ProductDao {

    public ArrayList<Product> displayProducts() {

        ArrayList<Product> productsBag = new ArrayList<>();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {

            String sql_ordered = "SELECT * FROM product WHERE status='active' ORDER BY selling_name ASC";

            ResultSet rs = statement.executeQuery(sql_ordered);

            while (rs.next()) {

                int product_id = rs.getInt("product_id");

                String selling_name = rs.getString("selling_name");
                Double selling_unit = rs.getDouble("selling_unit");
                String baking_name = rs.getString("baking_name");
                Double baking_unit = rs.getDouble("baking_unit");
                int product_code = rs.getInt("product_code");

                Product product = new Product();
                product.setProduct_id(product_id);
                product.setSelling_name(selling_name);
                product.setSelling_unit(selling_unit);
                product.setBaking_name(baking_name);
                product.setBaking_unit(baking_unit);
                product.setProduct_code(product_code);

                productsBag.add(product);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productsBag;

    }

    Product getProductByID(int product_id) {
        Product product = null;

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM product WHERE product_id='" + product_id + "'";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String selling_name = rs.getString("selling_name");
                Double selling_unit = rs.getDouble("selling_unit");
                String baking_name = rs.getString("baking_name");
                Double baking_unit = rs.getDouble("baking_unit");
                int product_code = rs.getInt("product_code");
                //  String last = rs.getString("last_name");
                //Display values
                product = new Product();
                product.setProduct_id(product_id);
                product.setSelling_name(selling_name);
                product.setSelling_unit(selling_unit);
                product.setBaking_name(baking_name);
                product.setBaking_unit(baking_unit);
                product.setProduct_code(product_code);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return product;
    }

    public void deleteProductByProductID(int product_id) {

        String deleteProductSQL = "UPDATE product SET status='deleted' WHERE product_id=?";
        String deleteFavorite_productSQL = "DELETE FROM favorite_product WHERE product_id=?";
        String deleteTemplate_ItemtSQL = "DELETE FROM template_item WHERE product_id=?";
        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement deleteProduct = connection.prepareStatement(deleteProductSQL);
                PreparedStatement deleteFavorite_product = connection.prepareStatement(deleteFavorite_productSQL);
                PreparedStatement deleteTemplate_Itemt = connection.prepareStatement(deleteTemplate_ItemtSQL);) {

            deleteProduct.setInt(1, product_id);
            deleteProduct.execute();

            deleteFavorite_product.setInt(1, product_id);
            deleteFavorite_product.execute();

            deleteTemplate_Itemt.setInt(1, product_id);
            deleteTemplate_Itemt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertNewProduct(Product product) {
        String deactivateUserSQL = "INSERT INTO product(selling_name, selling_unit, baking_name, baking_unit, product_code, status) VALUES (?,?,?,?,?,?)";
        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement insertProduct = connection.prepareStatement(deactivateUserSQL);) {

            insertProduct.setString(1, product.getSelling_name());
            insertProduct.setDouble(2, product.getSelling_unit());
            insertProduct.setString(3, product.getBaking_name());
            insertProduct.setDouble(4, product.getBaking_unit());
            insertProduct.setInt(5, product.getProduct_code());
            insertProduct.setString(6, product.getStatus());

            insertProduct.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

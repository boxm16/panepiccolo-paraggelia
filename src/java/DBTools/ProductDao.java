/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBTools;

import Models.Product;
import java.sql.Connection;
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

    public List<Product> displayProducts() {

        List<Product> productsBag = new ArrayList<>();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {

            String sql_ordered = "SELECT * FROM product ORDER BY selling_name ASC";

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

}

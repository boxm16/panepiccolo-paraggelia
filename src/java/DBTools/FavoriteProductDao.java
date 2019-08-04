/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBTools;

import Models.Label;
import Models.Product;
import Models.User;
import Models.FavoriteProduct;
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
public class FavoriteProductDao {

    public List<FavoriteProduct> getFavoriteProductsByCustomerID(int user_id) {

        FavoriteProduct favoriteProduct = null;
        ArrayList<FavoriteProduct> favoriteProductsBag = new ArrayList();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {

            String SQL_QUERY = "SELECT favorite_product.product_id, selling_name, favorite_product.label_id, label_description FROM favorite_product "
                    + "INNER JOIN product on favorite_product.product_id = product.product_id "
                    + "INNER JOIN label on favorite_product.label_id=label.label_id "
                    + "WHERE user_id='" + user_id + "';";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);

            while (resultSet.next()) {

                int product_id = resultSet.getInt("product_id");
                String selling_name = resultSet.getString("selling_name");
                int label_id = resultSet.getInt("label_id");
                String label_description = resultSet.getString("label_description");

                favoriteProduct = new FavoriteProduct();

                User user = new User();
                user.setUser_id(user_id);

                favoriteProduct.setUser(user);

                Product product = new Product();
                product.setProduct_id(product_id);
                product.setSelling_name(selling_name);
                favoriteProduct.setProduct(product);

                Label label = new Label();
                label.setLabel_id(label_id);
                label.setLabel_description(label_description);
                favoriteProduct.setLabel(label);

                favoriteProductsBag.add(favoriteProduct);

            }
        } catch (SQLException ex) {
            Logger.getLogger(FavoriteProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return favoriteProductsBag;

    }

    public void InsertFavoriteProduct(FavoriteProduct favoriteProduct) {

        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO  favorite_product(user_id, product_id, label_id, product_X, X_label)VALUES(?,?,?,?,?); ")) {
            prepStatement.setInt(1, favoriteProduct.getUser().getUser_id());
            prepStatement.setInt(2, favoriteProduct.getProduct().getProduct_id());
            prepStatement.setInt(3, favoriteProduct.getLabel().getLabel_id());
            prepStatement.setInt(4, favoriteProduct.getProduct_X());
            prepStatement.setInt(5, favoriteProduct.getX_label());
            prepStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteFavoriteProduct(int user_id, int product_id) {

        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM  favorite_product WHERE user_id=? AND product_id=?;");) {
            prepStatement.setInt(1, user_id);
            prepStatement.setInt(2, product_id);

            prepStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

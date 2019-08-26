/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBTools;

import Models.Label;
import Models.LabelsListItem;
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
public class LabelDao {

    public ArrayList<Label> displayLabels() {

        ArrayList<Label> labelsBag = new ArrayList();

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {
            String SQL_QUERY = "SELECT * FROM label WHERE label_status='active';";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);

            while (resultSet.next()) {
                Label label = new Label();
                int label_id = resultSet.getInt("label_id");
                String label_description = resultSet.getString("label_description");

                label.setLabel_id(label_id);
                label.setLabel_description(label_description);

                labelsBag.add(label);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return labelsBag;

    }

    public List<LabelsListItem> getLabelsListItems_LockedOrders() {

        List<LabelsListItem> getLabelsListItems_LockedOrders = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {

            String sql;

            sql = "SELECT label_description, SUM(quantity/product_X*X_label) "
                    + "FROM order_item "
                    + "INNER JOIN pp_order ON order_item.order_id=pp_order.order_id "
                    + "INNER JOIN favorite_product ON favorite_product.product_id=order_item.product_id "
                    + "INNER JOIN label ON label.label_id=favorite_product.label_id "
                    + "WHERE pp_order.status='locked' AND favorite_product.user_id=pp_order.orderer "
                    + "GROUP BY label_description;";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                String label_description = rs.getString(1);
                double label_id_count = rs.getDouble(2);
                Label label = new Label();

                label.setLabel_description(label_description);
                LabelsListItem labelListItem = new LabelsListItem();
                labelListItem.setLabel(label);
                labelListItem.setQuantity(label_id_count);
                getLabelsListItems_LockedOrders.add(labelListItem);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getLabelsListItems_LockedOrders;

    }

    public List<LabelsListItem> getLabelsListItems_ActiveOrders() {

        List<LabelsListItem> getLabelsListItems_LockedOrders = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {

            String sql;

            sql = "SELECT label_description, SUM(quantity/product_X*X_label) "
                    + "FROM order_item "
                    + "INNER JOIN pp_order ON order_item.order_id=pp_order.order_id "
                    + "INNER JOIN favorite_product ON favorite_product.product_id=order_item.product_id "
                    + "INNER JOIN label ON label.label_id=favorite_product.label_id "
                    + "WHERE pp_order.status='active' AND favorite_product.user_id=pp_order.orderer "
                    + "GROUP BY label_description;";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                String label_description = rs.getString(1);
                double label_id_count = rs.getDouble(2);
                Label label = new Label();

                label.setLabel_description(label_description);
                LabelsListItem labelListItem = new LabelsListItem();
                labelListItem.setLabel(label);
                labelListItem.setQuantity(label_id_count);
                getLabelsListItems_LockedOrders.add(labelListItem);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getLabelsListItems_LockedOrders;

    }

    public void inserLabel(Label label) {
        String inserLabelSQL = "INSERT INTO label(label_description, label_status) VALUES (?,?)";
        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement insertLabel = connection.prepareStatement(inserLabelSQL);) {

            insertLabel.setString(1, label.getLabel_description());

            insertLabel.setString(2, label.getLabel_status());

            insertLabel.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteLabelByLabelId(int label_id) {

        String deleteLabelSQL = "UPDATE label SET label_status='deleted' WHERE label_id=?";
        try (Connection connection = DataBaseConnection.getInitConnection();
                PreparedStatement deleteLabel = connection.prepareStatement(deleteLabelSQL);) {

            deleteLabel.setInt(1, label_id);
            deleteLabel.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

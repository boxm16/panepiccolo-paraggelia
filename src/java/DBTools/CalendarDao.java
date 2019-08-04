/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBTools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michail Sitmalidis
 */
@Repository
public class CalendarDao {

    public String checkTheDate(String due_day) {
        String date = "open";
        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {

            String sql = "SELECT * FROM calendar WHERE date='" + due_day + "' ;";
            ResultSet resaltSet = statement.executeQuery(sql);

            if (resaltSet.next() == false) {

                String reason = "Something is wrong with the date you try to place the order. Check the date again. Date can not be prior to tomorrow and later than 2049-07-01 ";
                date = reason;
            } else {
                do {
                    String status = resaltSet.getString("status");
                    if (status.equals("LOCKED")) {
                        String reason = resaltSet.getString("reason");
                        date = reason;
                    }
                } while (resaltSet.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CalendarDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return date;
    }

    public void lockTheDay() {

        ZoneId athensZone = ZoneId.of("Europe/Athens");
        LocalDate localDate = LocalDate.now(athensZone);
        LocalDate dateToLock = localDate.plusDays(1);

        try (Connection connection = DataBaseConnection.getInitConnection();
                Statement statement = connection.createStatement();) {
            String sql = "UPDATE calendar SET status = 'LOCKED', reason='ORDER DEADLINE PASSED' WHERE date='" + dateToLock + "';";

            statement.executeUpdate(sql);

            sql = "UPDATE pp_order SET status = 'locked' WHERE status='active' and due_day='" + dateToLock + "';";
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

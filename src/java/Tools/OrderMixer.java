/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import DBTools.OrderDao;
import DBTools.OrderTemplateDao;
import Models.Order;
import Models.OrderItem;
import Models.TemplateItem;
import Models.Product;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michail Sitmalidis
 */
@Repository
public class OrderMixer {

    public static Map<Integer, Order> getOrdersMapFromOrderTemplateItems(List<TemplateItem> templateItemsList) {
        Map<Integer, Order> ordersMap = new HashMap<>();

        for (TemplateItem templateItem : templateItemsList) {
            if (ordersMap.keySet().contains(templateItem.getUser_id())) {
                OrderItem orderItem = new OrderItem();
                Product product = new Product();
                product.setProduct_id(templateItem.getProduct().getProduct_id());
                orderItem.setProduct(product);
                orderItem.setQuantity(templateItem.getDay_quantity());

                ordersMap.get(templateItem.getUser_id()).getOrderItems().add(orderItem);

            } else {
                Order order = new Order();

                ZoneId athensZone = ZoneId.of("Europe/Athens");
                LocalDate now = LocalDate.now(athensZone);
                LocalDate due_day;
                DayOfWeek SATURDAY = DayOfWeek.valueOf("SATURDAY");
                if (now.getDayOfWeek() == SATURDAY) {
                    due_day = now.plusDays(2);
                } else {
                    due_day = now.plusDays(1);
                }
                Date datetime = new Date();
                SimpleDateFormat formatedDatetime = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
                order.setCreation_time(formatedDatetime.format(datetime));
                order.setDue_day(due_day.toString());

                order.setOrderer(templateItem.getUser_id());
                order.setStatus("active");

                OrderItem orderItem = new OrderItem();
                Product product = new Product();
                product.setProduct_id(templateItem.getProduct().getProduct_id());
                orderItem.setProduct(product);
                orderItem.setQuantity(templateItem.getDay_quantity());
                order.getOrderItems().add(orderItem);
                ordersMap.put(templateItem.getUser_id(), order);
            }

        }
        System.out.println(ordersMap.size());
        return ordersMap;
    }

}

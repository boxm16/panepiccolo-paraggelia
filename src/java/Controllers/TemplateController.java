/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DBTools.OrderTemplateDao;
import DBTools.UserDao;
import Models.OrderItem;
import Models.OrderTemplate;
import Models.TemplateItem;
import Models.User;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author boxm1
 */
@Controller
public class TemplateController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderTemplateDao orderTemplateDao;

    @RequestMapping(value = "/OrderPlan.htm", method = RequestMethod.GET)
    public String orderPlan(HttpSession session, ModelMap model, @RequestParam(value = "user_id") int user_id) {
        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";

        if (user.getRole().equals("admin") | user.getRole().equals("customer")) {
            User customer = userDao.getUserByID(user_id);

            List<TemplateItem> templateItems = orderTemplateDao.getOrderTemplate(user_id);

            if (templateItems.size() > 0) {
                OrderTemplate orderTemplate = new OrderTemplate();
                orderTemplate.setUser_id(user_id);

                orderTemplate.setTemplateItems(templateItems);
                model.addAttribute("orderTemplate", orderTemplate);

                model.addAttribute("customer", customer);
            } else {

                message = "ORDER AUTOGENERATION IS NOT ACTIVATED";
                model.addAttribute("message", message);
                model.addAttribute("customer", customer);
            }
            if (user.getRole().equals("admin")) {
                returnPoint = "OrderPlan_Admin";
            }
            if (user.getRole().equals("customer")) {
                returnPoint = "OrderPlan_Customer";
            }
        }

        return returnPoint;

    }

    @RequestMapping(value = "/saveTemplate", method = RequestMethod.POST)
    public String storeCategory2(HttpSession session, ModelMap model, OrderTemplate orderTemplate) {
        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";

        if (user.getRole().equals("admin") | user.getRole().equals("customer")) {
            orderTemplateDao.updateOrderTemplate(orderTemplate);
            if(user.getRole().equals("admin")) {
                returnPoint="redirect:AdminMainPage.htm";
            }
            if(user.getRole().equals("customer")){
             returnPoint="redirect:CustomerMainPage.htm";}
        }
        return returnPoint;
    }
}

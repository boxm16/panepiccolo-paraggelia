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
    public String orderPlan(ModelMap model, @RequestParam(value = "user_id") int user_id) {

        User customer = userDao.getUserByID(user_id);

        //List<OrderItem> orderItems = orderTemplateDao.getOrderTemplate(user_id, day);
        List<TemplateItem> templateItems = orderTemplateDao.getOrderTemplate(user_id);

        if (templateItems.size() > 0) {
            OrderTemplate orderTemplate = new OrderTemplate();
            orderTemplate.setUser_id(user_id);

            orderTemplate.setTemplateItems(templateItems);
            model.addAttribute("orderTemplate", orderTemplate);

            //  model.addAttribute("favoriteProductsList", favoriteProductsList);
            model.addAttribute("customer", customer);
        } else {

            String message = "You have not yet activated Order Autogenerator";
            model.addAttribute("message", message);
            model.addAttribute("customer", customer);
        }

        return "OrderPlan";

    }

    @RequestMapping(value = "/saveTemplate", method = RequestMethod.POST)
    public String storeCategory2(ModelMap model, OrderTemplate orderTemplate) {

          orderTemplateDao.updateOrderTemplate(orderTemplate);
        return "redirect:AdminMainPage.htm";
    }
}

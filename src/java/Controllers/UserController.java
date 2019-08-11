/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DBTools.OrderDao;
import DBTools.UserDao;
import Models.Customer;
import Models.Order;
import Models.User;
import java.util.LinkedHashMap;
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
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @RequestMapping(value = "/loginFormHandling.htm", method = RequestMethod.POST)
    public String login(HttpSession session, ModelMap model, @RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userDao.checkUserByUsername(username);
        if (user.getUsername() != null) {
            if (password.equals(user.getPassword())) {
                user.setPassword(null);

                session.setAttribute("user", user);
                /*
                System.out.println("session ID at login: " + session.getId());
                System.out.println("user from Session at login: " + session.getAttribute("user"));
                System.out.println("session creation time at login: " + session.getCreationTime());

                User user1 = (User) session.getAttribute("user");
                System.out.println("Username1 at login:" + user1.getUsername());
                 */
                String message = "HI " + user.getUsername();
                model.addAttribute("message", message);
                model.addAttribute("users", user);
                if (user.getRole().equals("admin")) {
                    return "redirect:/AdminMainPage.htm";
                }
                if (user.getRole().equals("baker")) {
                    return "BakerMainPage";
                }
                if (user.getRole().equals("observer")) {
                    return "redirect:/ObserverPage.htm";
                }
                if (user.getRole().equals("customer")) {
                    return "CustomerMainPage";
                } else {
                    message = "User role not defined";
                    model.addAttribute("message", message);
                    return "index";
                }

            } else {
                String message = "Password is wrong";
                model.addAttribute("message", message);
                return "index";
            }
        } else {

            String message = "Username doesn't exist";
            model.addAttribute("message", message);
            return "index";
        }

    }

    @RequestMapping(value = "/AdminMainPage.htm", method = RequestMethod.GET)
    public String MainPage4(ModelMap model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!user.getRole().equals("admin")) {
            return "index";
        }
        LinkedHashMap<Integer, Customer> customersMap = userDao.getCustomers();
        LinkedHashMap<Integer, Order> activeOrdersMap = orderDao.getActiveOrdersMap();

        LinkedHashMap<Integer, Customer> filledCustomersList = mixCustomer(customersMap, activeOrdersMap);

        model.addAttribute("activeOrdersMap", activeOrdersMap);
        model.addAttribute("filledCustomersList", filledCustomersList);

        return "AdminMainPage";
    }

    private LinkedHashMap<Integer, Models.Customer> mixCustomer(LinkedHashMap<Integer, Models.Customer> customersMap, LinkedHashMap<Integer, Order> activeOrdersMap) {

        for (Order order : activeOrdersMap.values()) {
            customersMap.get(order.getOrderer()).getOrdersList().add(order);
        }

        return customersMap;
    }
}

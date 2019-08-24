/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DBTools.OrderDao;
import DBTools.UserDao;
import Models.Customer;
import Models.CustomersRatingTable;
import Models.Order;
import Models.User;
import Validation.UserValidator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private UserValidator userValidator;

//this is for test only
    @RequestMapping(value = "/Customers.htm", method = RequestMethod.GET)
    public String Customers(ModelMap model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!user.getRole().equals("admin")) {
            return "index";
        }
        LinkedHashMap<Integer, Customer> customersMap = userDao.getCustomers();//no need for Map, change later for ArrayList
        ArrayList<Customer> activeCustomers = new ArrayList<>();
        ArrayList<Customer> deactivatedCustomers = new ArrayList<>();
        for (Customer customer : customersMap.values()) {
            if (customer.getUser().getStatus().equals("active")) {
                activeCustomers.add(customer);
            }
            if (customer.getUser().getStatus().equals("deactivated")) {
                deactivatedCustomers.add(customer);
            }

        }

        model.addAttribute("activeCustomers", activeCustomers);
        model.addAttribute("deactivatedCustomers", deactivatedCustomers);
        return "Customers";
    }

    @RequestMapping(value = "/saveCustomerRating", method = RequestMethod.POST)

    public String saveCustomerRating(ModelMap model, CustomersRatingTable customersRatingTable, HttpSession session) {

        userDao.updateUsersRating(customersRatingTable);
        return "redirect:/Customers.htm";

    }

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
                    return "redirect:/CustomerMainPage.htm";
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
        LinkedHashMap<Integer, Customer> customersMap = userDao.getActiveCustomers();
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

    @RequestMapping(value = "/CustomerMainPage.htm", method = RequestMethod.GET)
    public String CustomerMainPage(ModelMap model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!user.getRole().equals("customer")) {
            return "index";
        }
        LinkedHashMap<Integer, Order> myActiveOrdersMap = orderDao.getActiveOrdersMapByUserID(user.getUser_id());
        model.addAttribute("myActiveOrdersMap", myActiveOrdersMap);
        model.addAttribute("user", user);
        return "CustomerMainPage";
    }

    @RequestMapping(value = "/Logout.htm", method = RequestMethod.GET)
    public String Logout(HttpSession session) {

        session.invalidate();

        return "index";

    }

    @RequestMapping(value = "createNewCustomer.htm", method = RequestMethod.GET)
    public String createNewCustomer(HttpSession session, ModelMap model) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }
        User user = new User();
        model.addAttribute(user);
        return "CreateNewCustomer";

    }

    @RequestMapping(value = "createNewCustomerHandling.htm", method = RequestMethod.POST)
    public String createNewCustomerHandling(ModelMap model, User user, BindingResult bindingResult, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {

            return "CreateNewCustomer";
        } else {

            userDao.inserNewCustomer(user);

            return "redirect:/Customers.htm";
        }
    }

    @RequestMapping(value = "/deactivateUser.htm", method = RequestMethod.GET)
    public String deactivateUser(HttpSession session, @RequestParam(value = "user_id") int user_id) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }

        userDao.deactivateUserByUserID(user_id);

        return "redirect:/Customers.htm";
    }

    @RequestMapping(value = "/reactivateUser.htm", method = RequestMethod.GET)
    public String reactivateUser(HttpSession session, @RequestParam(value = "user_id") int user_id) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }

        userDao.reactivateUserByUserID(user_id);

        return "redirect:/Customers.htm";
    }

    @RequestMapping(value = "/editCustomer.htm", method = RequestMethod.GET)
    public String editUser(HttpSession session, @RequestParam(value = "user_id") int user_id, ModelMap model) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }

        User userCustomer = userDao.getUserByID(user_id);
        model.addAttribute("userCustomer", userCustomer);
        return "EditCustomer";
    }

    @RequestMapping(value = "/editCustomerHandling.htm", method = RequestMethod.POST)
    public String editCustomerHandling(HttpSession session, User userCustomer, BindingResult bindingResult, ModelMap model) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }

        userValidator.validate(userCustomer, bindingResult);
        if (bindingResult.hasErrors()) {

            model.addAttribute("userCustomer", userCustomer);
            return "EditCustomer";
        } else {
            userDao.updateUserCustomer(userCustomer);

            return "redirect:/Customers.htm";
        }
    }

    @RequestMapping(value = "/Observers.htm", method = RequestMethod.GET)
    public String Observers(ModelMap model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!user.getRole().equals("admin")) {
            return "index";
        }
        ArrayList<User> observers = userDao.getObservers();

        model.addAttribute("observers", observers);

        return "Observers";
    }

    ///
    @RequestMapping(value = "createNewObserver.htm", method = RequestMethod.GET)
    public String createNewObserver(HttpSession session, ModelMap model) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }
        User user = new User();
        model.addAttribute(user);
        return "CreateNewObserver";

    }

    @RequestMapping(value = "createNewObserverHandling.htm", method = RequestMethod.POST)
    public String createNewObserverHandling(ModelMap model, User user, BindingResult bindingResult, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {

            return "CreateNewObserver";
        } else {

            userDao.inserNewObserver(user);

            return "redirect:/Observers.htm";
        }
    }

    @RequestMapping(value = "/deleteObserver.htm", method = RequestMethod.GET)
    public String deleteProduct(HttpSession session, @RequestParam(value = "user_id") int user_id) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }
        userDao.deleteUserByUserId(user_id);

        return "redirect:/Observers.htm";

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DBTools.CalendarDao;
import DBTools.FavoriteProductDao;
import DBTools.OrderDao;
import DBTools.OrderTemplateDao;
import DBTools.UserDao;
import Models.Customer;
import Models.FavoriteProduct;
import Models.Order;
import Models.OrderItem;
import Models.OrderTemplate;
import Models.ProductOrderer;
import Models.User;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashMap;
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
 * @author Michail Sitmalidis
 */
@Controller
public class OrderController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FavoriteProductDao favoriteProductsDao;

    @Autowired
    private CalendarDao calendarDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderTemplateDao orderTemplateDao;

    @RequestMapping(value = "/NewOrderPage.htm", method = RequestMethod.GET)

    public String newOrderPage(HttpSession session, ModelMap model, @RequestParam(value = "user_id") int user_id) {

        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";

        if (user.getRole().equals("admin") | user.getRole().equals("customer")) {
            User customer = userDao.getUserByID(user_id);

            List< FavoriteProduct> favoriteProductsList = favoriteProductsDao.getFavoriteProductsByCustomerID(user_id);

            ZoneId athensZone = ZoneId.of("Europe/Athens");
            LocalDate date = LocalDate.now(athensZone);

            DayOfWeek SATURDAY = DayOfWeek.valueOf("SATURDAY");
            if (date.getDayOfWeek() == SATURDAY) {
                date = date.plusDays(2);
            } else {
                date = date.plusDays(1);
            }

            model.addAttribute("due_day1", date);
            model.addAttribute("favoriteProductsList", favoriteProductsList);
            model.addAttribute("customer", customer);
            if (user.getRole().equals("admin")) {
                returnPoint = "NewOrderPage_Admin";
            }
            if (user.getRole().equals("customer")) {
                returnPoint = "NewOrderPage_Customer";
            }
        }
        return returnPoint;
    }

    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    public String saveOrder(ModelMap model, Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user.getRole().equals("admin") | user.getRole().equals("customer")) {

            String dateStatus = calendarDao.checkTheDate(order.getDue_day());

            if (!dateStatus.equals("open")) {
                String reason = dateStatus;
                String message = "This date is locked:" + reason + ". Try another date";

                model.addAttribute("message", message);

                return "ErrorPage";
            }
            if (orderDao.checkOrder(order)) {

                String message = "There has already  been  placed some order for this day. You can modify existing order, or cancel it and place a new order";
                model.addAttribute("message", message);
                return "ErrorPage";
            } else {
                orderDao.insertOrder(order);
                if (user.getRole().equals("admin")) {

                    return "redirect:/AdminMainPage.htm";
                } else {

                    return "redirect:/CustomerMainPage.htm";
                }
            }
        } else {
            return "index";
        }
    }

    @RequestMapping(value = "/ModifyOrderPage.htm", method = RequestMethod.GET)
    public String goToModifyOrderPage(ModelMap model, HttpSession session, @RequestParam(value = "order_id") int order_id
    ) {
        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";
        if (user.getRole().equals("admin") | user.getRole().equals("customer")) {
            Order order = orderDao.getOrderByID(order_id);
            model.addAttribute("order", order);
            if (user.getRole().equals("admin")) {
                returnPoint = "ModifyOrderPage_Admin";
            }
            if (user.getRole().equals("customer")) {
                returnPoint = "ModifyOrderPage_Customer";
            }
        }
        return returnPoint;
    }

    @RequestMapping(value = "/modifyOrder", method = RequestMethod.POST)
    public String modifyOrder(ModelMap model, Order order,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";
        if (user.getRole().equals("observer")) {
            message = "";
            returnPoint = "index";
        }

        if (user.getRole().equals("admin") | user.getRole().equals("customer")) {
            int newOrderId = orderDao.insertOrder(order);
            orderDao.modifyOrder(order, newOrderId, session);

            if (user.getRole().equals("admin")) {

                returnPoint = "redirect:/AdminMainPage.htm";
            }

            if (user.getRole().equals("customer")) {

                returnPoint = "redirect:/CustomerMainPage.htm";
            }
        }
        return returnPoint;
    }

    @RequestMapping(value = "/cancelOrder", method = RequestMethod.GET)
    public String CancelOrder(ModelMap model, Order order,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");

        if (!user.getRole().equals("admin")) {
            return "index";
        }
        orderDao.cancelOrder(order, session);

        return "redirect:/AdminMainPage.htm";
    }

    @RequestMapping(value = "/autogenerator.htm", method = RequestMethod.GET)
    public String autogenerator(ModelMap model,
            @RequestParam(value = "user_id") int user_id,
            @RequestParam(value = "day") String day
    ) {

        User customer = userDao.getUserByID(user_id);

        List<OrderItem> orderItems = orderTemplateDao.getOrderTemplate(user_id, day);
        if (orderItems.size() > 0) {
            Order order = new Order();
            order.setOrderer(user_id);
            order.setOrderItems(orderItems);
            model.addAttribute("autoGeneratedOrder", order);

            //  model.addAttribute("favoriteProductsList", favoriteProductsList);
            model.addAttribute("customer", customer);
        } else {

            String message = "You have not yet activated Order Autogenerator";
            model.addAttribute("message", message);
            model.addAttribute("customer", customer);
        }

        return "AutogeneratorAdminPage";

    }

    @RequestMapping(value = "/autogeneratorActivation.htm", method = RequestMethod.GET)
    public String autogeneratorActivation(HttpSession session, ModelMap model, @RequestParam(value = "user_id") int user_id) {
        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";
        if (user.getRole().equals("observer")) {
            message = "";
            returnPoint = "index";
        }

        if (user.getRole().equals("admin") | user.getRole().equals("customer")) {
            if (!orderTemplateDao.autogeneratorActivated(user_id)) {

                List< FavoriteProduct> favoriteProductsList = favoriteProductsDao.getFavoriteProductsByCustomerID(user_id);

                orderTemplateDao.activateAutogenerator(user_id, favoriteProductsList);
            }
            message = "";
            returnPoint = "redirect:/OrderPlan.htm?user_id=" + user_id + "&day=NoDay";
        }
        model.addAttribute("message", message);
        return returnPoint;

    }

    @RequestMapping(value = "/autogeneratorDeactivation.htm", method = RequestMethod.GET)
    public String autogeneratorDeactivation(HttpSession session, ModelMap model, @RequestParam(value = "user_id") int user_id
    ) {
        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";
        if (user.getRole().equals("admin") | user.getRole().equals("customer")) {
            orderTemplateDao.deactivateAutogenerator(user_id);
            returnPoint = "redirect:/OrderPlan.htm?user_id=" + user_id + "&day=NoDay";
        }
        model.addAttribute("message", message);
        return returnPoint;

    }

    @RequestMapping(value = "/LockedOrdersPage.htm", method = RequestMethod.GET)
    public String LockedOrders(HttpSession session, ModelMap model
    ) {
        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";
        if (user.getRole().equals("customer")) {
            message = "";
            returnPoint = "index";
        }
        //inner join query , no need for 2 connections, change it
        LinkedHashMap<Integer, Customer> customersMap = userDao.getActiveCustomers();
        LinkedHashMap<Integer, Order> lockedOrdersMap = orderDao.getLockedOrdersMap();

        LinkedHashMap<Integer, Customer> filledCustomersList_LockedOrders = mixCustomer(customersMap, lockedOrdersMap);
        model.addAttribute("message", message);
        model.addAttribute("activeOrdersMap", lockedOrdersMap);
        model.addAttribute("filledCustomersList_LockedOrders", filledCustomersList_LockedOrders);
        if (user.getRole().equals("admin")) {
            message = "";
            returnPoint = "LockedOrdersPage_Admin";
        }
        if (user.getRole().equals("observer")) {
            message = "";
            returnPoint = "LockedOrdersPage_Staff";
        }
        return returnPoint;
    }

    @RequestMapping(value = "/ObserverPage.htm", method = RequestMethod.GET)
    public String ObserverPage_ActiveOrders(HttpSession session, ModelMap model
    ) {
        User user = (User) session.getAttribute("user");

        if (!user.getRole().equals("observer")) {
            return "index";
        }

        LinkedHashMap<Integer, Customer> customersMap = userDao.getActiveCustomers();
        LinkedHashMap<Integer, Order> activeOrdersMap = orderDao.getActiveOrdersMap();

        LinkedHashMap<Integer, Customer> filledCustomersList_ActiveOrders = mixCustomer(customersMap, activeOrdersMap);
        model.addAttribute("activeOrdersMap", activeOrdersMap);
        model.addAttribute("filledCustomersList_ActiveOrders", filledCustomersList_ActiveOrders);

        return "ObserverPage";
    }

    @RequestMapping(value = "/ObserverPage_LockedOrders.htm", method = RequestMethod.GET)
    public String ObserverPage_LockedOrders(HttpSession session, ModelMap model
    ) {
        User user = (User) session.getAttribute("user");

        if (!user.getRole().equals("observer")) {
            return "index";
        }

        LinkedHashMap<Integer, Customer> customersMap = userDao.getActiveCustomers();
        LinkedHashMap<Integer, Order> lockedOrdersMap = orderDao.getLockedOrdersMap();

        LinkedHashMap<Integer, Customer> filledCustomersList_LockedOrders = mixCustomer(customersMap, lockedOrdersMap);
        model.addAttribute("lockedOrdersMap", lockedOrdersMap);
        model.addAttribute("filledCustomersList_LockedOrders", filledCustomersList_LockedOrders);

        return "ObserverPage_LockedOrders";
    }

    public LinkedHashMap<Integer, Customer> mixCustomer(LinkedHashMap<Integer, Customer> customersMap, LinkedHashMap<Integer, Order> activeOrdersMap) {

        for (Order order : activeOrdersMap.values()) {
            customersMap.get(order.getOrderer()).getOrdersList().add(order);
        }

        return customersMap;
    }

    @RequestMapping(value = "/OrderedItemsList_ActiveOrders", method = RequestMethod.GET)
    public String OrderedItemsList(HttpSession session, ModelMap model) {
        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";
        if (user.getRole().equals("customer")) {
            message = "";
            returnPoint = "index";

        }
        if (user.getRole().equals("admin")) {
            List<OrderItem> OrderedItemsBakingUnitSummary_ActiveOrders = orderDao.getOrderedItemsBakingUnitSummary_ActiveOrders();
            model.addAttribute("OrderedItemsBakingUnitSummary_ActiveOrders", OrderedItemsBakingUnitSummary_ActiveOrders);
            message = "";
            returnPoint = "OrderedItems_ActiveOrders_Admin";

        }
        if (user.getRole().equals("observer")) {
            List<OrderItem> OrderedItemsBakingUnitSummary_ActiveOrders = orderDao.getOrderedItemsBakingUnitSummary_ActiveOrders();
            model.addAttribute("OrderedItemsBakingUnitSummary_ActiveOrders", OrderedItemsBakingUnitSummary_ActiveOrders);
            message = "";
            returnPoint = "OrderedItems_ActiveOrders_Staff";
        }
        model.addAttribute("message", message);
        return returnPoint;
    }

    @RequestMapping(value = "/OrderedItemsList_LockedOrders", method = RequestMethod.GET)
    public String OrderedItemsList_LockedOrders(HttpSession session, ModelMap model) {

        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";
        if (user.getRole().equals("customer")) {
            message = "";
            returnPoint = "index";

        }
        if (user.getRole().equals("admin")) {
            List<OrderItem> OrderedItemsBakingUnitSummary_LockedOrders = orderDao.getOrderedItemsBakingUnitSummary_LockedOrders();
            model.addAttribute("OrderedItemsBakingUnitSummary_LockedOrders", OrderedItemsBakingUnitSummary_LockedOrders);
            message = "";
            returnPoint = "OrderedItems_LockedOrders_Admin";

        }
        if (user.getRole().equals("observer")) {
            List<OrderItem> OrderedItemsBakingUnitSummary_LockedOrders = orderDao.getOrderedItemsBakingUnitSummary_LockedOrders();
            model.addAttribute("OrderedItemsBakingUnitSummary_LockedOrders", OrderedItemsBakingUnitSummary_LockedOrders);
            message = "";
            returnPoint = "OrderedItems_LockedOrders_Staff";
        }
        model.addAttribute("message", message);
        return returnPoint;

    }

    @RequestMapping(value = "/OrderedItemsList_ObserverPage", method = RequestMethod.GET)

    public String OrderedItemsList_ObserverPage(HttpSession session, ModelMap model) {
        User user = (User) session.getAttribute("user");

        if (user.getRole().equals("customer")) {
            return "index";
        }
        List<OrderItem> OrderedItemsBakingUnitSummary_LockedOrders = orderDao.getOrderedItemsBakingUnitSummary_LockedOrders();

        model.addAttribute("OrderedItemsBakingUnitSummary_LockedOrders", OrderedItemsBakingUnitSummary_LockedOrders);
        return "OrderedItemsList_ObserverPage";

    }

    @RequestMapping(value = "/ProductOrderers", method = RequestMethod.GET)
    public String productOrderers_ActiveOrders(HttpSession session, ModelMap model, @RequestParam(value = "product_id") int product_id) {
        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";
        if (user.getRole().equals("customer")) {
            message = "";
            returnPoint = "index";

        }
        List<ProductOrderer> productOrderersList = orderDao.getProductOrderersList_ActiveOrders(product_id);
        model.addAttribute("productOrderersList", productOrderersList);
        model.addAttribute("message", message);

        if (user.getRole().equals("admin")) {
            message = "";
            returnPoint = "ProductOrderersPage_ActiveOrders_Admin";

        }
        if (user.getRole().equals("observer")) {
            message = "";
            returnPoint = "ProductOrderersPage_ActiveOrders_Staff";

        }
        return returnPoint;

    }

    @RequestMapping(value = "/ProductOrderers_LockedOrders", method = RequestMethod.GET)

    public String productOrderers_LockedOrders(HttpSession session, ModelMap model, @RequestParam(value = "product_id") int product_id) {
        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";
        if (user.getRole().equals("customer")) {
            message = "";
            returnPoint = "index";

        }
        List<ProductOrderer> productOrderersList = orderDao.getProductOrderersList_LockedOrders(product_id);
        model.addAttribute("productOrderersList", productOrderersList);
        model.addAttribute("message", message);

        if (user.getRole().equals("admin")) {
            message = "";
            returnPoint = "ProductOrderersPage_LockedOrders_Admin";

        }
        if (user.getRole().equals("observer")) {
            message = "";
            returnPoint = "ProductOrderersPage_LockedOrders_Staff";

        }
        return returnPoint;
    }

    @RequestMapping(value = "/ProductOrderers_LockedOrders_ObserverPage", method = RequestMethod.GET)
    public String ProductOrderers_LockedOrders_ObserverPage(HttpSession session, ModelMap model, @RequestParam(value = "product_id") int product_id) {
        User user = (User) session.getAttribute("user");

        if (user.getRole().equals("customer")) {
            return "index";
        }
        List<ProductOrderer> productOrderersList = orderDao.getProductOrderersList_LockedOrders(product_id);

        model.addAttribute("productOrderersList", productOrderersList);
        return "ProductOrderersPage_LockedOrders_ObserverPage";

    }

    @RequestMapping(value = "/displayOrder.htm", method = RequestMethod.GET)
    public String dispalyOrder(ModelMap model, HttpSession session, @RequestParam(value = "order_id") int order_id) {

        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";
        if (user.getRole().equals("customer")) {
            message = "";
            returnPoint = "index";

        }
        Order order = orderDao.getOrderByID(order_id);

        model.addAttribute("order", order);
        model.addAttribute("message", message);

        if (user.getRole().equals("admin")) {
            message = "";
            returnPoint = "DisplayOrder_Admin";

        }
        if (user.getRole().equals("observer")) {
            message = "";
            returnPoint = "DisplayOrder_Staff";

        }
        return returnPoint;

    }

    @RequestMapping(value = "/LoadMyLastOrder.htm", method = RequestMethod.GET)
    public String loadMyLastOrder(ModelMap model, HttpSession session, @RequestParam(value = "user_id") int user_id) {
        User user = (User) session.getAttribute("user");
        String message = "User`s status undefined.";
        String returnPoint = "ErrorPage";

        if (user.getRole().equals("admin") | user.getRole().equals("customer")) {
            Order order = orderDao.getMyLastOrder(user_id);
            User customer = userDao.getUserByID(user_id);

            ZoneId athensZone = ZoneId.of("Europe/Athens");
            LocalDate date = LocalDate.now(athensZone);

            DayOfWeek SATURDAY = DayOfWeek.valueOf("SATURDAY");
            if (date.getDayOfWeek() == SATURDAY) {
                date = date.plusDays(2);
            } else {
                date = date.plusDays(1);
            }

            model.addAttribute("due_day1", date);
            model.addAttribute("customer", customer);
            model.addAttribute("order", order);

            if (user.getRole().equals("admin")) {
                message = "";
                returnPoint = "MyLastOrderPage_Admin";
            }
            if (user.getRole().equals("customer")) {
                message = "";
                returnPoint = "MyLastOrderPage_Customer";
            }
        }

        model.addAttribute("message", message);
        return returnPoint;
    }

}

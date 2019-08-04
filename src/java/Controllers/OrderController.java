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

    @RequestMapping(value = "/AdminOrderPage.htm", method = RequestMethod.GET)

    public String adminOrderPage(ModelMap model, @RequestParam(value = "user_id") int user_id) {

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

        return "AdminOrderPage";
    }

    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    public String saveOrder(ModelMap model, Order order, HttpSession session) {
        String dateStatus = calendarDao.checkTheDate(order.getDue_day());

        System.out.println("user from session at saveOrder: " + session.getAttribute("user"));

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
            return "redirect:/AdminMainPage.htm";
        }
    }

    @RequestMapping(value = "/ModifyOrderPage.htm", method = RequestMethod.GET)
    public String goToModifyOrderPage(ModelMap model, HttpSession session, @RequestParam(value = "order_id") int order_id) {

        System.out.println("session at ModifyOrderPage: " + session);
        System.out.println("user from session at ModifyOrderPage: " + session.getAttribute("user"));

        Order order = orderDao.getOrderByID(order_id);
        model.addAttribute("order", order);
        return "ModifyOrderPage";
    }

    @RequestMapping(value = "/modifyOrder", method = RequestMethod.POST)
    public String modifyOrder(ModelMap model, Order order, HttpSession session) {

        System.out.println("user from session at modifyOrderButtonClick: " + session.getAttribute("user"));

        int newOrderId = orderDao.insertOrder(order);
        orderDao.modifyOrder(order, newOrderId, session);
        return "redirect:/AdminMainPage.htm";
    }

    @RequestMapping(value = "/cancelOrder", method = RequestMethod.GET)
    public String CancelOrder(ModelMap model, Order order, HttpSession session) {

        orderDao.cancelOrder(order, session);

        return "redirect:/AdminMainPage.htm";
    }

    @RequestMapping(value = "/autogenerator.htm", method = RequestMethod.GET)
    public String autogenerator(ModelMap model, @RequestParam(value = "user_id") int user_id, @RequestParam(value = "day") String day) {

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
    public String autogeneratorActivation(ModelMap model, @RequestParam(value = "user_id") int user_id) {

        if (!orderTemplateDao.autogeneratorActivated(user_id)) {

            List< FavoriteProduct> favoriteProductsList = favoriteProductsDao.getFavoriteProductsByCustomerID(user_id);

            orderTemplateDao.activateAutogenerator(user_id, favoriteProductsList);
        }
        return "redirect:/OrderPlan.htm?user_id=" + user_id + "&day=NoDay";

    }

    @RequestMapping(value = "/autogeneratorDeactivation.htm", method = RequestMethod.GET)
    public String autogeneratorDeactivation(ModelMap model, @RequestParam(value = "user_id") int user_id) {

        orderTemplateDao.deactivateAutogenerator(user_id);

        return "redirect:/OrderPlan.htm?user_id=" + user_id + "&day=NoDay";

    }

    @RequestMapping(value = "/LockedOrdersPage.htm", method = RequestMethod.GET)
    public String LockedOrders(ModelMap model) {

        LinkedHashMap<Integer, Customer> customersMap = userDao.getCustomers();
        LinkedHashMap<Integer, Order> lockedOrdersMap = orderDao.getLockedOrdersMap();

        LinkedHashMap<Integer, Customer> filledCustomersList_LockedOrders = mixCustomer(customersMap, lockedOrdersMap);

        System.out.println("ActiveOrdersMap" + lockedOrdersMap.size());
        model.addAttribute("activeOrdersMap", lockedOrdersMap);
        model.addAttribute("filledCustomersList_LockedOrders", filledCustomersList_LockedOrders);

        return "LockedOrdersPage";
    }

    @RequestMapping(value = "/ObserverPage.htm", method = RequestMethod.GET)
    public String LockedOrdersObserverPage(ModelMap model) {

        LinkedHashMap<Integer, Customer> customersMap = userDao.getCustomers();
        LinkedHashMap<Integer, Order> lockedOrdersMap = orderDao.getLockedOrdersMap();

        LinkedHashMap<Integer, Customer> filledCustomersList_LockedOrders = mixCustomer(customersMap, lockedOrdersMap);

        System.out.println("ActiveOrdersMap" + lockedOrdersMap.size());
        model.addAttribute("activeOrdersMap", lockedOrdersMap);
        model.addAttribute("filledCustomersList_LockedOrders", filledCustomersList_LockedOrders);

        return "ObserverPage";
    }

    public LinkedHashMap<Integer, Customer> mixCustomer(LinkedHashMap<Integer, Customer> customersMap, LinkedHashMap<Integer, Order> activeOrdersMap) {

        for (Order order : activeOrdersMap.values()) {
            customersMap.get(order.getOrderer()).getOrdersList().add(order);
        }

        return customersMap;
    }

    @RequestMapping(value = "/OrderedItemsList_ActiveOrders", method = RequestMethod.GET)
    public String OrderedItemsList(ModelMap model) {
        List<OrderItem> OrderedItemsBakingUnitSummary_ActiveOrders = orderDao.getOrderedItemsBakingUnitSummary_ActiveOrders();

        model.addAttribute("OrderedItemsBakingUnitSummary_ActiveOrders", OrderedItemsBakingUnitSummary_ActiveOrders);
        return "OrderedItemsBakingUnitSummary_ActiveOrders";

    }

    @RequestMapping(value = "/OrderedItemsList_LockedOrders", method = RequestMethod.GET)
    public String OrderedItemsList_LockedOrders(ModelMap model) {
        List<OrderItem> OrderedItemsBakingUnitSummary_LockedOrders = orderDao.getOrderedItemsBakingUnitSummary_LockedOrders();

        model.addAttribute("OrderedItemsBakingUnitSummary_LockedOrders", OrderedItemsBakingUnitSummary_LockedOrders);
        return "OrderedItemsBakingUnitSummary_LockedOrders";

    }

    @RequestMapping(value = "/OrderedItemsList_ObserverPage", method = RequestMethod.GET)

    public String OrderedItemsList_ObserverPage(ModelMap model) {
        List<OrderItem> OrderedItemsBakingUnitSummary_LockedOrders = orderDao.getOrderedItemsBakingUnitSummary_LockedOrders();

        model.addAttribute("OrderedItemsBakingUnitSummary_LockedOrders", OrderedItemsBakingUnitSummary_LockedOrders);
        return "OrderedItemsList_ObserverPage";

    }

    @RequestMapping(value = "/ProductOrderers", method = RequestMethod.GET)
    public String productOrderers_ActiveOrders(ModelMap model,
            @RequestParam(value = "product_id") int product_id
    ) {

        List<ProductOrderer> productOrderersList = orderDao.getProductOrderersList_ActiveOrders(product_id);

        model.addAttribute("productOrderersList", productOrderersList);
        return "ProductOrderersPage";

    }

    @RequestMapping(value = "/ProductOrderers_LockedOrders", method = RequestMethod.GET)

    public String productOrderers_LockedOrders(ModelMap model,
            @RequestParam(value = "product_id") int product_id
    ) {

        List<ProductOrderer> productOrderersList = orderDao.getProductOrderersList_LockedOrders(product_id);

        model.addAttribute("productOrderersList", productOrderersList);
        return "ProductOrderersPage_LockedOrders";

    }

    
      @RequestMapping(value = "/ProductOrderers_LockedOrders_ObserverPage", method = RequestMethod.GET)
 public String ProductOrderers_LockedOrders_ObserverPage(ModelMap model,
            @RequestParam(value = "product_id") int product_id
    ) {

        List<ProductOrderer> productOrderersList = orderDao.getProductOrderersList_LockedOrders(product_id);

        model.addAttribute("productOrderersList", productOrderersList);
        return "ProductOrderersPage_LockedOrders_ObserverPage";

    }

    @RequestMapping(value = "/displayOrder.htm", method = RequestMethod.GET)
    public String dispalyOrder(ModelMap model, HttpSession session, @RequestParam(value = "order_id") int order_id) {

        Order order = orderDao.getOrderByID(order_id);
        model.addAttribute("order", order);
        return "DisplayOrder";
    }

    @RequestMapping(value = "/LoadMyLastOrder.htm", method = RequestMethod.GET)
    public String loadMyLastOrder(ModelMap model, HttpSession session, @RequestParam(value = "user_id") int user_id) {

        // Order order = orderDao.getOrderByID(myLastOrderId);
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
        return "MyLastOrderPage";
    }

}
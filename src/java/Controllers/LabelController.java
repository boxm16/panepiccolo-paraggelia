/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DBTools.LabelDao;
import Models.Label;
import Models.LabelsListItem;
import Models.User;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Michail Sitmalidis
 */
@Controller
public class LabelController {

    @Autowired
    private LabelDao labelDao;

    @RequestMapping(value = "/LabelsList_LockedOrders", method = RequestMethod.GET)
    public String OrderedItemsList_LockedOrders(ModelMap model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user.getRole().equals("customer")) {
            return "index";
        }
        List<LabelsListItem> orderedItemsLabelsList_LockedOrders = labelDao.getLabelsListItems_LockedOrders();
        model.addAttribute("orderedItemsLabelsList_LockedOrders", orderedItemsLabelsList_LockedOrders);
        return "OrderedItemsLabelsList_LockedOrders";

    }

    @RequestMapping(value = "/LabelsList_ActiveOrders", method = RequestMethod.GET)
    public String OrderedItemsList_ActiveOrders(ModelMap model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user.getRole().equals("customer")) {
            return "index";
        }
        List<LabelsListItem> orderedItemsLabelsList_LockedOrders = labelDao.getLabelsListItems_ActiveOrders();
        model.addAttribute("orderedItemsLabelsList_ActiveOrders", orderedItemsLabelsList_LockedOrders);
        return "OrderedItemsLabelsList_ActiveOrders";

    }

}

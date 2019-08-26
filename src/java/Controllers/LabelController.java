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
import java.util.ArrayList;
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

    @RequestMapping(value = "/Labels.htm", method = RequestMethod.GET)
    public String Labels(HttpSession session, ModelMap model) {
        User user = (User) session.getAttribute("user");
        if (!user.getRole().equals("admin")) {
            return "index";
        }

        ArrayList<Label> labels = labelDao.displayLabels();

        model.addAttribute("labels", labels);
        return "Labels";
    }

    @RequestMapping(value = "/createNewLabel.htm", method = RequestMethod.GET)
    public String createNewLabel(HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }
        return "CreateNewLabel";
    }

    @RequestMapping(value = "/createNewLabelHandling.htm", method = RequestMethod.POST)
    public String createNewProductHandling(HttpSession session, ModelMap model, Label label) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }
        label.setLabel_status("active");
        labelDao.inserLabel(label);
        return "redirect:/Labels.htm";
    }

    @RequestMapping(value = "/deleteLabel.htm", method = RequestMethod.GET)
    public String deleteProduct(HttpSession session, @RequestParam(value = "label_id") int label_id) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }
       labelDao.deleteLabelByLabelId(label_id);

        return "redirect:/Labels.htm";

    }

}

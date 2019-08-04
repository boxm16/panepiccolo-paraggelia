/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Order;
import Models.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michail Sitmalidis
 */
public class Customer {

    private User user;
    List<Order> ordersList = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrdersList(List<Order> ordersList) {
        this.ordersList = ordersList;
    }

    public User getUser() {
        return user;
    }

    public List<Order> getOrdersList() {
        return ordersList;
    }

    

}

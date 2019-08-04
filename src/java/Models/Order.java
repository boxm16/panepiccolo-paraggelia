/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author boxm1
 */
public class Order {

    private int order_id;
    private int orderer;
    private String status;
    private String creation_time;
    private String due_day;
    private int operator;
    private String operation_time;

    private List<OrderItem> orderItems = new ArrayList<>();

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setOrderer(int orderer) {
        this.orderer = orderer;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public void setDue_day(String due_day) {
        this.due_day = due_day;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public void setOperation_time(String operation_time) {
        this.operation_time = operation_time;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getOrderer() {
        return orderer;
    }

    public String getStatus() {
        return status;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public String getDue_day() {
        return due_day;
    }

    public int getOperator() {
        return operator;
    }

    public String getOperation_time() {
        return operation_time;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    
    

}

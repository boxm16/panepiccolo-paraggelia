/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author boxm1
 */
public class Product {

    private int product_id;
    private String selling_name;
    private Double selling_unit;
    private String baking_name;
    private Double baking_unit;
    private int product_code;
    private String status;

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setSelling_name(String selling_name) {
        this.selling_name = selling_name;
    }

    public void setSelling_unit(Double selling_unit) {
        this.selling_unit = selling_unit;
    }

    public void setBaking_name(String baking_name) {
        this.baking_name = baking_name;
    }

    public void setBaking_unit(Double baking_unit) {
        this.baking_unit = baking_unit;
    }

    public void setProduct_code(int product_code) {
        this.product_code = product_code;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getSelling_name() {
        return selling_name;
    }

    public Double getSelling_unit() {
        return selling_unit;
    }

    public String getBaking_name() {
        return baking_name;
    }

    public Double getBaking_unit() {
        return baking_unit;
    }

    public int getProduct_code() {
        return product_code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    
}

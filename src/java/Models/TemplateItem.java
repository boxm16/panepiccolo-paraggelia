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
public class TemplateItem {

    private int user_id;
    private Product product;
    private int day_1;
    private int day_2;
    private int day_3;
    private int day_4;
    private int day_5;
    private int day_6;
    private int day_7;
    private int day_quantity;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setDay_1(int day_1) {
        this.day_1 = day_1;
    }

    public void setDay_2(int day_2) {
        this.day_2 = day_2;
    }

    public void setDay_3(int day_3) {
        this.day_3 = day_3;
    }

    public void setDay_4(int day_4) {
        this.day_4 = day_4;
    }

    public void setDay_5(int day_5) {
        this.day_5 = day_5;
    }

    public void setDay_6(int day_6) {
        this.day_6 = day_6;
    }

    public void setDay_7(int day_7) {
        this.day_7 = day_7;
    }

    public int getUser_id() {
        return user_id;
    }

    public Product getProduct() {
        return product;
    }

    public int getDay_1() {
        return day_1;
    }

    public int getDay_2() {
        return day_2;
    }

    public int getDay_3() {
        return day_3;
    }

    public int getDay_4() {
        return day_4;
    }

    public int getDay_5() {
        return day_5;
    }

    public int getDay_6() {
        return day_6;
    }

    public int getDay_7() {
        return day_7;
    }

    public void setDay_quantity(int day_quantity) {
        this.day_quantity = day_quantity;
    }

    public int getDay_quantity() {
        return day_quantity;
    }

}

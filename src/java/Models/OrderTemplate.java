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
public class OrderTemplate {

    private int user_id;
    private List<TemplateItem> templateItems = new ArrayList<>();
    private boolean day_1;
    private boolean day_2;
    private boolean day_3;
    private boolean day_4;
    private boolean day_5;
    private boolean day_6;
    private boolean day_7;

    public int getUser_id() {
        return user_id;
    }

    public List<TemplateItem> getTemplateItems() {
        return templateItems;
    }

    public boolean isDay_1() {
        return day_1;
    }

    public boolean isDay_2() {
        return day_2;
    }

    public boolean isDay_3() {
        return day_3;
    }

    public boolean isDay_4() {
        return day_4;
    }

    public boolean isDay_5() {
        return day_5;
    }

    public boolean isDay_6() {
        return day_6;
    }

    public boolean isDay_7() {
        return day_7;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setTemplateItems(List<TemplateItem> templateItems) {
        this.templateItems = templateItems;
    }

    public void setDay_1(boolean day_1) {
        this.day_1 = day_1;
    }

    public void setDay_2(boolean day_2) {
        this.day_2 = day_2;
    }

    public void setDay_3(boolean day_3) {
        this.day_3 = day_3;
    }

    public void setDay_4(boolean day_4) {
        this.day_4 = day_4;
    }

    public void setDay_5(boolean day_5) {
        this.day_5 = day_5;
    }

    public void setDay_6(boolean day_6) {
        this.day_6 = day_6;
    }

    public void setDay_7(boolean day_7) {
        this.day_7 = day_7;
    }

    

}

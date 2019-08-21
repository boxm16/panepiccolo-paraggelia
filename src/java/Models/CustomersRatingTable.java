/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;

/**
 *
 * @author Michail Sitmalidis
 */
public class CustomersRatingTable {
    private ArrayList<User> customersRatingTable=new ArrayList<>();

    public void setCustomersRatingTable(ArrayList<User> customersRatingTable) {
        this.customersRatingTable = customersRatingTable;
    }

    public ArrayList<User> getCustomersRatingTable() {
        return customersRatingTable;
    }

  
    
}

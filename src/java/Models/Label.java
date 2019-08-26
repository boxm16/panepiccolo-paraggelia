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
public class Label {
    
    private int label_id;
    private String label_description;
    private String label_status;

    public void setLabel_id(int label_id) {
        this.label_id = label_id;
    }

    public void setLabel_description(String label_description) {
        this.label_description = label_description;
    }

    public int getLabel_id() {
        return label_id;
    }

    public String getLabel_description() {
        return label_description;
    }

    public void setLabel_status(String label_status) {
        this.label_status = label_status;
    }

    public String getLabel_status() {
        return label_status;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Michail Sitmalidis
 */
public class LabelsListItem {

    private Label label;
    private double quantity;

    public void setLabel(Label label) {
        this.label = label;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Label getLabel() {
        return label;
    }

    public double getQuantity() {
        return quantity;
    }

    

}

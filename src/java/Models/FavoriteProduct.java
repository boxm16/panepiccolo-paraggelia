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
public class FavoriteProduct {

    private User user;
    private Product product;
    private Label label;
    private int product_X;
    private int X_label;

    public void setUser(User user) {
        this.user = user;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public void setProduct_X(int product_X) {
        this.product_X = product_X;
    }

    public void setX_label(int X_label) {
        this.X_label = X_label;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public Label getLabel() {
        return label;
    }

    public int getProduct_X() {
        return product_X;
    }

    public int getX_label() {
        return X_label;
    }

   

    
   
    

}

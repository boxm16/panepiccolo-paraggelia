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
public class User {

    private int user_id;
    private String official_name;
    private String second_name;
    private String username;
    private String password;
    private String role;
    private String status;
    private int rating;
    private int customer_code;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setOfficial_name(String official_name) {
        this.official_name = official_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCustomer_code(int customer_code) {
        this.customer_code = customer_code;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getOfficial_name() {
        return official_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public int getRating() {
        return rating;
    }

    public int getCustomer_code() {
        return customer_code;
    }
    
    
    

}

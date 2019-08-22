/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validation;

import DBTools.UserDao;
import Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Michail Sitmalidis
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean supports(Class<?> type) {
         return User.class.equals(type);
   }

    @Override
    public void validate(Object o, Errors errors) {
        User u = (User) o;
        String username = u.getUsername().trim();

        if (userDao.checkUserByUsername(username) != null) {
            errors.rejectValue("username", "username.Exists");
        }

        String password = u.getPassword();
        if (password.length() < 4) {
            errors.rejectValue("password", "pwd.Short");
        }
        

        char[] chars = u.getUsername().toCharArray();
        for (char ch : chars) {

            if (Character.isWhitespace(ch)) {
                errors.rejectValue("username", "username.WhiteSpace");
            }

            if (ch == '\\') {
                errors.rejectValue("username", "username.SpecChar1");
            }
           

        }

    }

}

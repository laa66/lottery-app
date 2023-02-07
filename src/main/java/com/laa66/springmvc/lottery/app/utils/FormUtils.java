package com.laa66.springmvc.lottery.app.utils;

import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.entity.Role;
import com.laa66.springmvc.lottery.app.validate.UserForm;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;

public class FormUtils {

    public static User toUser(UserForm userForm, PasswordEncoder passwordEncoder) {
        User user = new User(userForm.getFirstName(), userForm.getLastName(),
                userForm.getUsername(), passwordEncoder.encode(userForm.getPassword()),
                LocalDate.parse(userForm.getBirthDate()), userForm.getEmail());
        user.setId(0);
        user.setEnabled(true);
        user.setRoles(Collections.singleton(new Role("ROLE_USER", user)));
        return user;
    }

}

package com.laa66.springmvc.lottery.app.controller;


import com.laa66.springmvc.lottery.app.entity.Role;
import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.service.UserService;
import com.laa66.springmvc.lottery.app.validate.UserValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/panel")
    public String showUserPanel() {
        return null;
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("userValid") UserValid userValid, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "signup";
        User user = new User(userValid.getFirstName(), userValid.getLastName(),
                userValid.getUsername(),
                userValid.getPassword(),
                LocalDate.parse(userValid.getBirthDate()),
                userValid.getEmail());
        userService.saveUser(user);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute Model model) {
        return null;
    }

    @DeleteMapping("/delete")
    public String deleteUser() {
        return null;
    }

    @PostMapping("/saveTicket")
    public String saveTicket() {
        return null;
    }


}

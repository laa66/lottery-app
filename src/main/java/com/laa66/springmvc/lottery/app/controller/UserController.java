package com.laa66.springmvc.lottery.app.controller;


import com.laa66.springmvc.lottery.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
    public String saveUser() {
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

package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/join")
public class JoinController {

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }
    @GetMapping("/signup")
    public String showRegistrationForm() {
        return "signup";
    }
}

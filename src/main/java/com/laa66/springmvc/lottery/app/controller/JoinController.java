package com.laa66.springmvc.lottery.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/join")
public class JoinController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @GetMapping("/signup")
    public String showRegistrationForm() {
        return "signup";
    }
}

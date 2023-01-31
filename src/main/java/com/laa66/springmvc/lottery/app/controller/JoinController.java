package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.validate.UserValid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  User endpoints for showing login/register page
 *
 */

@Controller
@RequestMapping("/join")
public class JoinController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userValid", new UserValid());
        return "signup";
    }
}

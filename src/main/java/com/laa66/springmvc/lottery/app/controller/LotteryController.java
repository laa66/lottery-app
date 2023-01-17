package com.laa66.springmvc.lottery.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LotteryController {

    @GetMapping("/")
    public String showHome(Model model) {

        return "index";
    }
}

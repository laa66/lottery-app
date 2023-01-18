package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.entity.DrawResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LotteryController {

    @GetMapping("/")
    public String showHome(Model model) {
        //test
        DrawResult drawResult = new DrawResult();
        drawResult.draw();
        //test
        model.addAttribute("lastNumbers", drawResult);
        return "index";
    }
}

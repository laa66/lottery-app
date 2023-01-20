package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.entity.DrawResult;
import com.laa66.springmvc.lottery.app.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;


    @GetMapping("/")
    public String showHome(Model model) {
        lotteryService.deleteDrawResult(10);
        return "index";
    }
}

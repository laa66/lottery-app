package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.entity.DrawResult;
import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.service.LotteryService;
import com.laa66.springmvc.lottery.app.service.TicketService;
import com.laa66.springmvc.lottery.app.service.UserService;
import com.laa66.springmvc.lottery.app.validate.TicketNumbersValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LotteryController {

    @Autowired
    private UserService userService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private TicketService ticketService;

    // TODO: 31.01.2023 REFACTOR THIS METHOD FOR GETTING USER PRINCIPLE (Change to inject because principles will be used in other controllers)
    @GetMapping("/")
    public String showHome(Authentication authentication, Model model) {
        DrawResult drawResult = lotteryService.getLastDrawResult();
        List<DrawResult> allNumbers = lotteryService.getDrawResults();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.loadRegularUserByUsername(authentication.getName());
            model.addAttribute("userLastTickets", ticketService.getUserTickets(user.getId()));
            model.addAttribute("loggedUserId", user.getId());
            model.addAttribute("ticketNumbers", new TicketNumbersValid());
        }
        model.addAttribute("lastNumbers", drawResult);
        model.addAttribute("allNumbers", allNumbers);
        return "index";
    }
}

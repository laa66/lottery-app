package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.entity.DrawResult;
import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.service.LotteryService;
import com.laa66.springmvc.lottery.app.service.TicketService;
import com.laa66.springmvc.lottery.app.service.UserService;
import com.laa66.springmvc.lottery.app.validate.TicketForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;

// TODO: 01.02.2023 $SERVICES? MAP USERS TO UserForm and implement USER PANEL and ADMIN PANEL
// TODO: 01.02.2023 $ENTITY_CLASSES DATE FORMATTING IN JS get rid of @Transient fields in class Ticket and class DrawResult
// TODO: 02.02.2023 $HOMEPAGE sorting your last number desc with date
// TODO: 05.02.2023 WHEN ADDING NEW TICKET SEND ALSO USER ID TO UPLOAD FRESH DATA TO CONFIRMATION PAGE
// TODO: 05.02.2023 $GLOBAL EXCEPTION HANDLING IN CONTROLLERS
@Controller
public class LotteryController {

    @Autowired
    private UserService userService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/")
    public String showHome(Authentication authentication, Model model) {
        DrawResult drawResult = lotteryService.getLastDrawResult();
        List<DrawResult> allNumbers = lotteryService.getDrawResults();

        model.addAttribute("lastNumbers", drawResult);
        model.addAttribute("allNumbers", allNumbers);

        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.loadRegularUserByUsername(authentication.getName());
            model.addAttribute("userTickets", ticketService.getUserTickets(user.getId()));
            model.addAttribute("loggedUserId", user.getId());
            model.addAttribute("ticketNumbers", new TicketForm());
        }
        return "index";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "error";
    }

    @GetMapping("/ticketConfirmation")
    public String showTicketConfirmation(Model model) {
        model.addAttribute("nextLotteryDate", lotteryService.getLastDrawResult()
                .getDate()
                .plusDays(1)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        return "ticket-confirmation";
    }

    @PostMapping("/draw")
    public String saveDrawResult() {
        return null;
    }
}

package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.service.UserService;
import com.laa66.springmvc.lottery.app.service.LotteryService;
import com.laa66.springmvc.lottery.app.service.TicketService;
import com.laa66.springmvc.lottery.app.dto.TicketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;


// TODO: 12.02.2023 REFACTOR Controllers Tests
// TODO: 13.02.2023 Create bean for drawing one time a day and refactor tests
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
        model.addAttribute("lastNumbers", lotteryService.getLastDrawResult());
        model.addAttribute("allNumbers", lotteryService.getDrawResults());

        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.loadRegularUserByUsername(authentication.getName());
            model.addAttribute("userTickets", ticketService.getUserTickets(user.getId()));
            model.addAttribute("loggedUserId", user.getId());
            model.addAttribute("ticketNumbers", new TicketDTO());
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
    @GetMapping("/draw")
    public String saveDrawResult(@RequestParam("loggedUserId") Integer id) {
        lotteryService.drawAndSave();
        return "redirect:/user/panel/" + id;
    }
}

package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.service.TicketService;
import com.laa66.springmvc.lottery.app.service.UserService;
import com.laa66.springmvc.lottery.app.validate.TicketNumbersValid;
import com.laa66.springmvc.lottery.app.validate.UserValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/panel")
    public String showUserPanel() {
        return null;
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("userValid") UserValid userValid, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "signup";
        User user = new User(userValid.getFirstName(), userValid.getLastName(),
                userValid.getUsername(),
                userValid.getPassword(),
                LocalDate.parse(userValid.getBirthDate()),
                userValid.getEmail());
        userService.saveUser(user);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateUser() {
        return null;
    }

    @DeleteMapping("/delete")
    public String deleteUser() {
        return null;
    }

    @PostMapping("/saveTicket")
    public String saveTicket(@Valid @ModelAttribute("ticketNumbers") TicketNumbersValid ticketNumbersValid, BindingResult bindingResult) {
        System.out.println(ticketNumbersValid);
        return "ticket-confirmation";
    }


}

package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.service.TicketService;
import com.laa66.springmvc.lottery.app.service.UserService;
import com.laa66.springmvc.lottery.app.validate.TicketForm;
import com.laa66.springmvc.lottery.app.validate.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

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
    public String saveUser(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "signup";
        User user = new User(userForm.getFirstName(), userForm.getLastName(),
                userForm.getUsername(),
                userForm.getPassword(),
                LocalDate.parse(userForm.getBirthDate()),
                userForm.getEmail());
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

    @PostMapping("/saveTicket/{id}")
    public String saveTicket(@PathVariable("id") int id, @Valid @ModelAttribute TicketForm ticketForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        ticketService.addTicket(id, new Ticket(new HashSet<>(List.of(
                ticketForm.getField1(),
                ticketForm.getField2(),
                ticketForm.getField3(),
                ticketForm.getField4(),
                ticketForm.getField5(),
                ticketForm.getField6()
        ))));
        return "redirect:/ticketConfirmation";
    }


}

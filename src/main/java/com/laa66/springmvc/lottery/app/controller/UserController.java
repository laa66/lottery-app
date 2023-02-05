package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.exception.UserNotFoundException;
import com.laa66.springmvc.lottery.app.service.LotteryService;
import com.laa66.springmvc.lottery.app.service.TicketService;
import com.laa66.springmvc.lottery.app.service.UserService;
import com.laa66.springmvc.lottery.app.validate.TicketForm;
import com.laa66.springmvc.lottery.app.validate.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    private LotteryService lotteryService;

    /**
     *  $USER_PANEL has functionality:
     *      -ROLE_USER: update account info, check draw history
     *      -ROLE_ADMIN: same as user, user management (with disabling accounts)
     *          and draw result management (create, delete)
     */
    @GetMapping("/panel/{id}")
    public String showUserPanel(@PathVariable("id") int id, Authentication authentication, Model model) {
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("userLogged", userService.getUser(id));
        model.addAttribute("userHistory", ticketService.getUserTickets(id));

        if (authentication.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

            model.addAttribute("users", userService.getUsers());
            model.addAttribute("drawResults", lotteryService.getDrawResults());
        }
        return "user-panel";
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

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        User user = userService.getUser(id);
        if (user != null) userService.deleteUser(user);
        else throw new UserNotFoundException("User with ID: " + id + " not found");
        return "redirect:/";
    }

    @PostMapping("/saveTicket/{id}")
    public String saveTicket(@PathVariable("id") int id, @Valid @ModelAttribute TicketForm ticketForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "redirect:/error";
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

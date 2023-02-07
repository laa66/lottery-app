package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.service.UserService;
import com.laa66.springmvc.lottery.app.entity.DrawResult;
import com.laa66.springmvc.lottery.app.service.LotteryService;
import com.laa66.springmvc.lottery.app.service.TicketService;
import com.laa66.springmvc.lottery.app.utils.FormUtils;
import com.laa66.springmvc.lottery.app.validate.TicketForm;
import com.laa66.springmvc.lottery.app.validate.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/panel/{id}")
    public String showUserPanel(@PathVariable("id") int id, Authentication authentication, Model model) {
        User user = userService.getUser(id);
        if (!user.getUsername().equals(authentication.getName())) throw new RuntimeException("Access denied.");
        Set<Ticket> tickets = ticketService.getUserTickets(id);
        model.addAttribute("loggedUserId", user.getId());
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("userLogged", userService.getUser(id));
        model.addAttribute("userHistory", tickets);
        model.addAttribute("userTicketSummary", tickets.size());

        if (authentication.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            List<User> users = userService.getUsers();
            List<DrawResult> drawResults = lotteryService.getDrawResults();
            model.addAttribute("allTicketSummary", ticketService.getAllTickets().size());
            model.addAttribute("allUserSummary", users.size());
            model.addAttribute("allDrawSummary", drawResults.size());
            model.addAttribute("users", users);
            model.addAttribute("drawResults", drawResults);
        }
        return "user-panel";
    }

    @GetMapping("/create")
    public String createUser(@RequestParam("loggedUserId") int loggedUserId, Model model) {
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("loggedUserId", loggedUserId);
        return "user-create";
    }

    @PostMapping("/save")
    public String saveUser(@RequestParam(required = false) Integer loggedUserId, @Valid @ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors() && loggedUserId == null) return "signup";
        else if (bindingResult.hasErrors()) return "redirect:/user/create?loggedUserId=" + loggedUserId;
        User user = FormUtils.toUser(userForm, passwordEncoder);
        userService.saveUser(user);
        if (loggedUserId == null) return "redirect:/";
        return "redirect:/user/panel/" + loggedUserId;
    }

    @PostMapping("/save/{id}")
    public String saveUser(@PathVariable("id") int id, @Valid @ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/user/panel/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, @RequestParam("loggedUserId") int loggedUserId) {
        User user = userService.getUser(id);
        if (user == null) throw new RuntimeException("Wrong user id");
        userService.deleteUser(user);
        return "redirect:/user/panel/" + loggedUserId;
    }

    @GetMapping("/info/{id}")
    public String showAdminUserPanel(Authentication authentication, @PathVariable("id") int id,  Model model) {
        User user = userService.getUser(id);
        model.addAttribute("loggedUserId", userService.loadRegularUserByUsername(authentication.getName()).getId());
        model.addAttribute("user", user);
        return "user-info";
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

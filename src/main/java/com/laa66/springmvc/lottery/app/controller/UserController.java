package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.exception.AccessErrorException;
import com.laa66.springmvc.lottery.app.exception.FormErrorException;
import com.laa66.springmvc.lottery.app.exception.UserNotFoundException;
import com.laa66.springmvc.lottery.app.service.UserService;
import com.laa66.springmvc.lottery.app.entity.DrawResult;
import com.laa66.springmvc.lottery.app.service.LotteryService;
import com.laa66.springmvc.lottery.app.service.TicketService;
import com.laa66.springmvc.lottery.app.dto.TicketDTO;
import com.laa66.springmvc.lottery.app.dto.UserDTO;
import com.laa66.springmvc.lottery.app.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private Mapper mapper;

    @GetMapping("/panel/{id}")
    public String showUserPanel(@PathVariable("id") int id, Authentication authentication, Model model) {
        User user = userService.getUser(id);
        if (!user.getUsername().equals(authentication.getName())) throw new AccessErrorException("Access denied.");
        Set<Ticket> tickets = ticketService.getUserTickets(id);
        model.addAttribute("loggedUserId", user.getId());
        model.addAttribute("userForm", new UserDTO());
        model.addAttribute("userLogged", user);
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
        model.addAttribute("userForm", new UserDTO());
        model.addAttribute("loggedUserId", loggedUserId);
        return "user-create";
    }

    @PostMapping("/save")
    public String saveUser(@RequestParam(required = false) Integer loggedUserId, @Valid @ModelAttribute("userForm") UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors() && loggedUserId == null) return "signup";
        else if (bindingResult.hasErrors()) return "redirect:/user/create?loggedUserId=" + loggedUserId;
        User user = mapper.toUser(userDTO);
        userService.saveUser(user);
        if (loggedUserId == null) return "redirect:/";
        return "redirect:/user/panel/" + loggedUserId;
    }

    @PostMapping("/save/{id}")
    public String saveUser(@PathVariable("id") int id, @Valid @ModelAttribute("user") User user, Authentication authentication) {
        boolean isNormalUser = authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (isNormalUser && id != user.getId()) throw new AccessErrorException("Access denied.");
        userService.saveUser(user);
        return "redirect:/user/panel/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, @RequestParam("loggedUserId") int loggedUserId) {
        User user = userService.getUser(id);
        if (user == null) throw new UserNotFoundException("Wrong user ID.");
        userService.deleteUser(user);
        return "redirect:/user/panel/" + loggedUserId;
    }

    @GetMapping("/info/{id}")
    public String showAdminUserPanel(Authentication authentication, @PathVariable("id") int id,  Model model) {
        User user = userService.getUser(id);
        if (user == null) throw new UserNotFoundException("Wrong user ID.");
        model.addAttribute("loggedUserId", userService.loadRegularUserByUsername(authentication.getName()).getId());
        model.addAttribute("user", user);
        return "user-info";
    }

    @PostMapping("/saveTicket/{id}")
    public String saveTicket(@PathVariable("id") int id, @Valid @ModelAttribute("ticketNumbers") TicketDTO ticketDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new FormErrorException("Wrong ticket numbers. Try again.");
        }
        ticketService.addTicket(id, mapper.toTicket(ticketDTO));
        return "redirect:/ticketConfirmation";
    }


}

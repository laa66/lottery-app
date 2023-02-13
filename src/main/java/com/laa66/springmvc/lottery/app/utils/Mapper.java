package com.laa66.springmvc.lottery.app.utils;

import com.laa66.springmvc.lottery.app.dto.TicketDTO;
import com.laa66.springmvc.lottery.app.dto.UserDTO;
import com.laa66.springmvc.lottery.app.entity.Role;
import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
public class Mapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User toUser(UserDTO userDTO) {
        User user = new User(userDTO.getFirstName(), userDTO.getLastName(),
                userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()),
                LocalDate.parse(userDTO.getBirthDate()), userDTO.getEmail());
        user.setId(0);
        user.setEnabled(true);
        user.setRoles(Collections.singleton(new Role("ROLE_USER", user)));
        return user;
    }

    public Ticket toTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setNumbers(new HashSet<>(List.of(
                ticketDTO.getField1(),
                ticketDTO.getField2(),
                ticketDTO.getField3(),
                ticketDTO.getField4(),
                ticketDTO.getField5(),
                ticketDTO.getField6()
        )));
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime drawDateTime = dateTime.getHour() < 22
                ? LocalDateTime.of(dateTime.toLocalDate(), LocalTime.of(22, 0))
                : LocalDateTime.of(dateTime.toLocalDate().plusDays(1), LocalTime.of(22,0));
        ticket.setDate(dateTime);
        ticket.setDrawDate(drawDateTime);
        return ticket;
    }
}


package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.dao.UserDAO;
import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public void addTicket(int id, Ticket ticket) {
        User user = userDAO.getUser(id);
        user.addTicket(createTicket(ticket, user));
    }

    @Override
    @Transactional
    public Set<Ticket> getUserTickets(int id) {
        Set<Ticket> tickets = userDAO.getUser(id).getTickets();
        Hibernate.initialize(tickets);
        return tickets;
    }

    // helpers
    private Ticket createTicket(Ticket ticket, User user) {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime drawDateTime = dateTime.getHour() < 22
                ? LocalDateTime.of(dateTime.toLocalDate(), LocalTime.of(22, 0))
                : LocalDateTime.of(dateTime.toLocalDate().plusDays(1), LocalTime.of(22,0));
        ticket.setDate(dateTime);
        ticket.setDrawDate(drawDateTime);
        ticket.setUser(user);
        return ticket;
    }
}

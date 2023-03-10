package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.dao.TicketDAO;
import com.laa66.springmvc.lottery.app.dao.UserDAO;
import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.exception.UserNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Override
    @Transactional
    public List<Ticket> getAllTickets() {
        return ticketDAO.getAllTickets();
    }

    @Override
    @Transactional
    public void addTicket(int id, Ticket ticket) throws UserNotFoundException {
        User user = userDAO.getUser(id);
        if (user == null)  throw new UserNotFoundException("Invalid User ID");
        ticket.setUser(user);
        user.addTicket(ticket);
    }

    @Override
    @Transactional
    public Set<Ticket> getUserTickets(int id) {
        Set<Ticket> tickets = userDAO.getUser(id).getTickets();
        Hibernate.initialize(tickets);
        return tickets;
    }
}

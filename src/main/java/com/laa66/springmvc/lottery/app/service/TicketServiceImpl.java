package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.dao.UserDAO;
import com.laa66.springmvc.lottery.app.entity.Ticket;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public void addTicket(String username, Ticket ticket) {

    }

    @Override
    @Transactional
    public Set<Ticket> getUserTickets(int id) {
        Set<Ticket> tickets = userDAO.getUser(id).getTickets();
        Hibernate.initialize(tickets);
        return tickets;
    }
}

package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.entity.Ticket;

import java.util.List;
import java.util.Set;

public interface TicketService {

    void addTicket(int id, Ticket ticket);

    List<Ticket> getAllTickets();

    Set<Ticket> getUserTickets(int id);
}

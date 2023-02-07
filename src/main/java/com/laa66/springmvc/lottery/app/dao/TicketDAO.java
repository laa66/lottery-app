package com.laa66.springmvc.lottery.app.dao;

import com.laa66.springmvc.lottery.app.entity.Ticket;

import java.util.List;

public interface TicketDAO {

    List<Ticket> getAllTickets();
}

package com.laa66.springmvc.lottery.app.dao;

import com.laa66.springmvc.lottery.app.entity.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketDAOImpl implements TicketDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Ticket> getAllTickets() {
        Session session = sessionFactory.getCurrentSession();
        Query<Ticket> tickets = session.createQuery("FROM Ticket", Ticket.class);
        return tickets.getResultList();
    }
}

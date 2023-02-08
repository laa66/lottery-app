package com.laa66.springmvc.lottery.app.dao;

import com.laa66.springmvc.lottery.app.config.SecurityConfig;
import com.laa66.springmvc.lottery.app.config.TestAppConfig;
import com.laa66.springmvc.lottery.app.entity.Role;
import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class, SecurityConfig.class})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TicketDAOImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    TicketDAO ticketDAO;

    @Autowired
    UserDAO userDAO;

    @BeforeEach
    void beforeTest() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user1 = new User("Max", "Newer", "laa66", "test",
                LocalDate.of(1997, 3, 9), "laa66@laa66.com");
        user1.setEnabled(true);
        user1.setRoles(Collections.singleton(new Role("ROLE_ADMIN", user1)));
        Ticket ticket1 = new Ticket(new HashSet<>(List.of(1, 17, 23, 44, 70, 99)));
        user1.addTicket(ticket1);
        Ticket ticket2 = new Ticket(new HashSet<>(List.of(15, 65, 12, 13, 1, 76)));
        user1.addTicket(ticket2);

        session.save(user1);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    @Transactional
    void testGetAllTicketsIfDbIsNotEmpty() {
        List<Ticket> tickets = ticketDAO.getAllTickets();
        assertNotNull(tickets);
        assertEquals(2, tickets.size());
    }

}
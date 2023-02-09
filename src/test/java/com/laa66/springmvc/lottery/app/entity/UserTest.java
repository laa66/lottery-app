package com.laa66.springmvc.lottery.app.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void beforeTest() {
        user = new User("Max", "Newer", "laa66", "test",
                LocalDate.of(1997, 3, 9), "laa66@laa66.com");
        user.setEnabled(true);
    }

    @Test
    void testAddTicketIfTicketIsNotNull() {
        user.addTicket(new Ticket());
        int sizeBeforeAdd = user.getTickets().size();
        user.addTicket(new Ticket());
        assertTrue(sizeBeforeAdd < user.getTickets().size());
    }

    @Test
    void testAddTicketIfTicketIsNull() {
        assertNull(user.getTickets());
        user.addTicket(new Ticket());
        assertNotNull(user.getTickets());
        assertEquals(1, user.getTickets().size());

    }

    @Test
    void testAddRoleIfRoleIsNotNull() {
        user.addRole(new Role());
        int sizeBeforeAdd = user.getRoles().size();
        user.addRole(new Role());
        assertTrue(sizeBeforeAdd < user.getRoles().size());
    }

    @Test
    void testAddRoleIfRoleIsNull() {
        assertNull(user.getRoles());
        user.addRole(new Role());
        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
    }
}
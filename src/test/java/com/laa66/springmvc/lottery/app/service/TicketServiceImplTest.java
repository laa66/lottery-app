package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.config.SecurityConfig;
import com.laa66.springmvc.lottery.app.config.TestAppConfig;
import com.laa66.springmvc.lottery.app.dao.TicketDAO;
import com.laa66.springmvc.lottery.app.dao.UserDAO;
import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class, SecurityConfig.class})
@WebAppConfiguration
class TicketServiceImplTest {

    @Mock
    TicketDAO ticketDAOMock;

    @Mock
    UserDAO userDAOMock;

    @InjectMocks
    TicketServiceImpl ticketService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTickets() {
        when(ticketDAOMock.getAllTickets()).thenReturn(List.of(new Ticket(), new Ticket(), new Ticket()));
        List<Ticket> list = ticketService.getAllTickets();

        assertEquals(3, list.size());
        verify(ticketDAOMock, times(1)).getAllTickets();
    }

    @Test
    void testAddTicketIfIdExists() {
        User user = new User();
        user.setId(1);
        assertNull(user.getTickets());
        when(userDAOMock.getUser(1)).thenReturn(user);
        ticketService.addTicket(1, new Ticket());

        assertNotNull(ticketService.getUserTickets(1));
        verify(userDAOMock, times(2)).getUser(1);
    }

    @Test
    void testAddTicketIfIdNotExists() {
        when(userDAOMock.getUser(1)).thenReturn(null);

        assertThrows(UserNotFoundException.class, ()-> ticketService.addTicket(1, new Ticket()));
        verify(userDAOMock, times(1)).getUser(1);
    }

    @Test
    void testGetUserTickets() {
        User user = new User();
        user.setId(1);
        user.addTicket(new Ticket());
        when(userDAOMock.getUser(1)).thenReturn(user);
        User loadedUser = userDAOMock.getUser(1);

        assertNotNull(loadedUser.getTickets());
        assertEquals(1, loadedUser.getId());
        assertEquals(1, loadedUser.getTickets().size());
        verify(userDAOMock, times(1)).getUser(1);
    }

}
package com.laa66.springmvc.lottery.app.util;

import com.laa66.springmvc.lottery.app.dto.TicketDTO;
import com.laa66.springmvc.lottery.app.dto.UserDTO;
import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    Mapper mapper = new Mapper();

    @BeforeEach
    void setup() {
        mapper.setPasswordEncoder(new BCryptPasswordEncoder());
    }

    @Test
    void testToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("test");
        userDTO.setLastName("testuser");
        userDTO.setUsername("user");
        userDTO.setPassword("test");
        userDTO.setBirthDate("1996-04-12");
        userDTO.setEmail("test@test.pl");
        User user = mapper.toUser(userDTO);
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertTrue(mapper.getPasswordEncoder().matches(userDTO.getPassword(), user.getPassword()));
        assertEquals(LocalDate.parse(userDTO.getBirthDate()), user.getBirthDate());
        assertEquals(userDTO.getEmail(), user.getEmail());
    }

    @Test
    void testToTicket() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setField1(1);
        ticketDTO.setField2(2);
        ticketDTO.setField3(3);
        ticketDTO.setField4(4);
        ticketDTO.setField5(5);
        ticketDTO.setField6(6);

        Ticket ticket = mapper.toTicket(ticketDTO);

        assertEquals(6, ticket.getNumbers().size());
        assertNotNull(ticket.getDate());
        assertNotNull(ticket.getDrawDate());
    }


}
package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.config.SecurityConfig;
import com.laa66.springmvc.lottery.app.config.TestAppConfig;
import com.laa66.springmvc.lottery.app.entity.DrawResult;
import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.service.LotteryService;
import com.laa66.springmvc.lottery.app.service.TicketService;
import com.laa66.springmvc.lottery.app.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class, SecurityConfig.class})
@WebAppConfiguration
class LotteryControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Mock
    UserService userServiceMock;

    @Mock
    TicketService ticketServiceMock;

    @Mock
    LotteryService lotteryServiceMock;

    @InjectMocks
    @Autowired
    LotteryController lotteryController;

    List<DrawResult> drawResults;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
        MockitoAnnotations.openMocks(this);

        DrawResult drawResult1 = new DrawResult();
        drawResult1.draw();
        DrawResult drawResult2 = new DrawResult();
        drawResult2.draw();
        drawResults = List.of(drawResult1, drawResult2);
    }

    @Test
    @WithAnonymousUser
    void testShowErrorPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/error"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", password = "test", roles = {"ADMIN"})
    void testSaveDrawResult() throws Exception {
        doNothing().when(lotteryServiceMock).drawAndSave();
        mockMvc.perform(MockMvcRequestBuilders.get("/draw")
                        .param("loggedUserId", "1"))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.redirectedUrl("/user/panel/1"))
                        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        verify(lotteryServiceMock, times(1)).drawAndSave();
    }

    @Test
    @WithMockUser(username = "testuser", password = "test", roles = {"ADMIN"})
    void testShowTicketConfirmation() throws Exception {
        when(lotteryServiceMock.getLastDrawResult()).thenReturn(drawResults.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/ticketConfirmation"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("nextLotteryDate"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ticket-confirmation"));
        verify(lotteryServiceMock, times(1)).getLastDrawResult();

    }

    @Test
    @WithAnonymousUser
    void testShowHomeWithAnonymousUser() throws Exception {
        when(lotteryServiceMock.getLastDrawResult()).thenReturn(drawResults.get(0));
        when(lotteryServiceMock.getDrawResults()).thenReturn(drawResults);

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("lastNumbers", "allNumbers"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("userTickets", "loggedUserId", "ticketNumbers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));

        verify(lotteryServiceMock, times(1)).getLastDrawResult();
        verify(lotteryServiceMock, times(1)).getDrawResults();
    }

    @Test
    @WithMockUser(username = "testuser", password = "test", roles = {"ADMIN"})
    void testShowHomeWithMockUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.addTicket(new Ticket());

        when(lotteryServiceMock.getLastDrawResult()).thenReturn(drawResults.get(0));
        when(lotteryServiceMock.getDrawResults()).thenReturn(drawResults);
        when(userServiceMock.loadRegularUserByUsername("testuser")).thenReturn(user);
        when(ticketServiceMock.getUserTickets(1)).thenReturn(user.getTickets());

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("ticketNumbers", "loggedUserId", "userTickets", "lastNumbers", "allNumbers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));

        verify(lotteryServiceMock, times(1)).getLastDrawResult();
        verify(lotteryServiceMock, times(1)).getDrawResults();
        verify(userServiceMock, times(1)).loadRegularUserByUsername("testuser");
        verify(ticketServiceMock, times(1)).getUserTickets(1);
    }




    @AfterEach
    void afterTest() {
        drawResults = null;
        reset(userServiceMock, lotteryServiceMock, ticketServiceMock);
    }






}
package com.laa66.springmvc.lottery.app.controller;

import com.laa66.springmvc.lottery.app.config.SecurityConfig;
import com.laa66.springmvc.lottery.app.config.TestAppConfig;
import com.laa66.springmvc.lottery.app.entity.DrawResult;
import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import com.laa66.springmvc.lottery.app.exception.AccessErrorException;
import com.laa66.springmvc.lottery.app.exception.FormErrorException;
import com.laa66.springmvc.lottery.app.exception.UserNotFoundException;
import com.laa66.springmvc.lottery.app.service.LotteryService;
import com.laa66.springmvc.lottery.app.service.TicketService;
import com.laa66.springmvc.lottery.app.service.UserService;
import com.laa66.springmvc.lottery.app.dto.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class, SecurityConfig.class})
@WebAppConfiguration
class UserControllerTest {

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
    UserController userController;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void testSaveTicketWhenFormIsCorrect() throws Exception {
        Ticket ticket = new Ticket(new HashSet<>(List.of(1,2,3,4,5,6)));
        doNothing().when(ticketServiceMock).addTicket(1, ticket);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/saveTicket/{id}",1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("field1", "1")
                        .param("field2", "2")
                        .param("field3", "3")
                        .param("field4", "4")
                        .param("field5", "5")
                        .param("field6", "6"))
                        .andDo(print())
                        .andExpect(status().is3xxRedirection())
                        .andExpect(MockMvcResultMatchers.redirectedUrl("/ticketConfirmation"));

        verify(ticketServiceMock, times(1)).addTicket(any(Integer.class), any(Ticket.class));
    }

    @Test
    @WithMockUser
    void testSaveTicketWhenFormIsIncorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/saveTicket/{id}", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("field1", "7")
                        .param("field2", "-52")
                        .param("field3", "51")
                        .param("field4", "99")
                        .param("field5", "5")
                        .param("field6", "6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof FormErrorException))
                .andExpect(MockMvcResultMatchers.view().name("error"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void testShowAdminUserPanelWithCorrectId() throws Exception {
        User user = new User();
        user.setId(1);
        User loggedUser = new User();
        user.setUsername("testuser");

        when(userServiceMock.getUser(1)).thenReturn(user);
        when(userServiceMock.loadRegularUserByUsername("testuser")).thenReturn(loggedUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/info/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user", "loggedUserId"))
                .andExpect(MockMvcResultMatchers.view().name("user-info"));

        verify(userServiceMock, times(1)).getUser(1);
        verify(userServiceMock, times(1)).loadRegularUserByUsername("testuser");

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testShowAdminUserPanelWithIncorrectId() throws Exception {
        when(userServiceMock.getUser(1)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/info/{id}", 1)
                        .with(csrf()))
                        .andDo(print())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.view().name("error"));

        verify(userServiceMock, times(1)).getUser(1);
    }

    @Test
    @WithMockUser
    void testShowAdminUserPanelWithoutAuthorization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/info/{id}", 1))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteUserWithCorrectId() throws Exception {
        User user = new User();
        user.setId(2);
        when(userServiceMock.getUser(2)).thenReturn(user);
        doNothing().when(userServiceMock).deleteUser(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/{id}", 2)
                .param("loggedUserId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/panel/1"))
                .andDo(print());
        verify(userServiceMock, times(1)).getUser(2);
        verify(userServiceMock, times(1)).deleteUser(user);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteUserWithIncorrectId() throws Exception {
        when(userServiceMock.getUser(2)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/{id}", 2)
                .param("loggedUserId", "1"))
                .andExpect(status().isOk())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andDo(print());
        verify(userServiceMock, times(1)).getUser(2);
    }

    @Test
    @WithMockUser
    void testDeleteUserWithoutAuthorization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/{id}", 2)
                .param("loggedUserId", "1"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testSaveUserAdminAuthorization() throws Exception {
        User user = new User();
        doNothing().when(userServiceMock).saveUser(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/save/{id}", 1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "2")
                .param("firstName", "")
                .param("lastName", "new")
                .param("username", "")
                .param("birthDate", "")
                .param("email", "")
                .param("enabled", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/panel/1"))
                .andDo(print());

        verify(userServiceMock, times(1)).saveUser(any(User.class));
    }

    @Test
    @WithMockUser
    void testSaveUserNormalUserAuthorizationWithCorrectIds() throws Exception {
        User user = new User();
        user.setId(1);
        doNothing().when(userServiceMock).saveUser(user);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/save/{id}", 1)
                .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .requestAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/panel/1"))
                .andDo(print()).andReturn();

        User loadedUser = (User) mvcResult.getModelAndView().getModel().get("user");
        assertEquals(user.getId(), loadedUser.getId());
        verify(userServiceMock, times(1)).saveUser(any(User.class));

    }

    @Test
    @WithMockUser
    void testSaveUserNormalUserAuthorizationWithIncorrectIds() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/save/{id}", 2)
                .with(csrf())
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AccessErrorException))
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    void testSaveUserNewUserWithCorrectForm() throws Exception {
        User user = new User();
        user.setId(0);
        doNothing().when(userServiceMock).saveUser(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/save")
                        .with(csrf())
                        .param("firstName", "test")
                        .param("lastName", "testuser")
                        .param("username", "test11")
                        .param("password", "Testpass$21")
                        .param("confirmPassword", "Testpass$21")
                        .param("birthDate", "1998-12-03")
                        .param("email", "test@gmail.com"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(MockMvcResultMatchers.redirectedUrl("/"))
                        .andDo(print());

        verify(userServiceMock, times(1)).saveUser(any(User.class));
    }

    @Test
    @WithAnonymousUser
    void testSaveUserNewUserWithIncorrectForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/save")
                        .with(csrf())
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("username", "test11")
                        .param("password", "test")
                        .param("birthDate", "2008-12-03")
                        .param("email", ""))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("signup"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testSaveUserAdminWithCorrectForm() throws Exception {
        User user = new User();
        doNothing().when(userServiceMock).saveUser(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/save")
                        .with(csrf())
                        .param("loggedUserId", "1")
                        .param("firstName", "test")
                        .param("lastName", "testuser")
                        .param("username", "test11")
                        .param("password", "Testpass$21")
                        .param("confirmPassword", "Testpass$21")
                        .param("birthDate", "1998-12-03")
                        .param("email", "test@gmail.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/panel/1"))
                .andDo(print());

        verify(userServiceMock, times(1)).saveUser(any(User.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testSaveUserAdminWithIncorrectForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/save")
                        .with(csrf())
                        .param("loggedUserId","1")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("username", "")
                        .param("password", "test")
                        .param("birthDate", "2008-12-03")
                        .param("email", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/create?loggedUserId=1"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateUserAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/create")
                .param("loggedUserId","1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("userForm", "loggedUserId"))
                .andExpect(MockMvcResultMatchers.view().name("user-create"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void testCreateUserNormalUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/create")
                .param("loggedUserId", "1"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "adminuser", password = "test", roles = "ADMIN")
    void testShowUserPanelAdminUser() throws Exception {
        User user1 = new User("test", "testuser", "adminuser", "test", LocalDate.now(), "admin@gmail.com");
        user1.setId(1);
        User user2 = new User("test", "testuser", "normaluser", "test", LocalDate.now(), "test@gmail.com");
        user2.setId(2);

        Set<Ticket> tickets1 = new HashSet<>(Set.of(new Ticket(), new Ticket()));
        Set<Ticket> tickets2 = new HashSet<>(Set.of(new Ticket(), new Ticket()));
        user1.setTickets(tickets1);
        user2.setTickets(tickets2);

        List<User> users = List.of(user1, user2);

        List<DrawResult> drawResults = List.of(new DrawResult(), new DrawResult());
        Set<Ticket> allTickets = new HashSet<>(tickets1);
        allTickets.addAll(tickets2);

        when(userServiceMock.getUser(1)).thenReturn(user1);
        when(ticketServiceMock.getUserTickets(1)).thenReturn(tickets1);
        when(userServiceMock.getUsers()).thenReturn(users);
        when(lotteryServiceMock.getDrawResults()).thenReturn(drawResults);
        when(ticketServiceMock.getAllTickets()).thenReturn(allTickets.stream().toList());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/panel/{id}", 1))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        assertEquals(1, mav.getModel().get("loggedUserId"));
        assertNotNull(mav.getModel().get("userForm"));
        assertTrue(mav.getModel().get("userForm") instanceof UserDTO);
        assertEquals(user1, mav.getModel().get("userLogged"));
        assertEquals(tickets1, mav.getModel().get("userHistory"));
        assertEquals(tickets1.size(), mav.getModel().get("userTicketSummary"));

        assertEquals(allTickets.size(), mav.getModel().get("allTicketSummary"));
        assertEquals(users.size(), mav.getModel().get("allUserSummary"));
        assertEquals(drawResults.size(), mav.getModel().get("allDrawSummary"));
        assertEquals(users, mav.getModel().get("users"));
        assertEquals(drawResults, mav.getModel().get("drawResults"));
        ModelAndViewAssert.assertViewName(mav, "user-panel");
    }

    @Test
    @WithMockUser(username = "testuser", password = "test")
    void testShowUserPanelNormalUser() throws Exception {
        User user = new User("test", "testuser", "testuser", "test", LocalDate.now(), "test@gmail.com");
        user.setId(1);
        Set<Ticket> tickets = new HashSet<>(Set.of(new Ticket(), new Ticket()));
        when(userServiceMock.getUser(1)).thenReturn(user);
        when(ticketServiceMock.getUserTickets(1)).thenReturn(tickets);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/panel/{id}", 1))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "user-panel");
        assertEquals(1, mav.getModel().get("loggedUserId"));
        assertNotNull(mav.getModel().get("userForm"));
        assertEquals(user, mav.getModel().get("userLogged"));
        assertNotNull(tickets);
        assertEquals(2, mav.getModel().get("userTicketSummary"));

    }

    @Test
    @WithMockUser(username = "testuser", password = "test")
    void testShowUserPanelWithIncorrectAuthorization() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");

        when(userServiceMock.getUser(1)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/panel/{id}", 1))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AccessErrorException))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andDo(print());
    }

    @AfterEach
    void afterTest() {
        reset(userServiceMock, lotteryServiceMock, ticketServiceMock);
    }

}
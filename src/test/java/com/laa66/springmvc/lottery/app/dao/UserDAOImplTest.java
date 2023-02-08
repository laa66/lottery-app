package com.laa66.springmvc.lottery.app.dao;

import com.laa66.springmvc.lottery.app.config.SecurityConfig;
import com.laa66.springmvc.lottery.app.config.TestAppConfig;
import com.laa66.springmvc.lottery.app.entity.Role;
import com.laa66.springmvc.lottery.app.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class, SecurityConfig.class})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserDAOImplTest {

    @Autowired
    SessionFactory sessionFactory;

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
        session.save(user1);

        User user2 = new User("Mike", "Funny", "mike1", "test",
                LocalDate.of(1998, 1,12), "mike@laa66.com");
        user2.setEnabled(true);
        user2.setRoles(Collections.singleton(new Role("ROLE_USER", user2)));
        session.save(user2);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    @Transactional
    void testGetUserByIdWhenIsPresent() {
        User user = userDAO.getUser(1);
        assertEquals(1, user.getId());
        assertEquals("Max", user.getFirstName());
        assertEquals("Newer", user.getLastName());
        assertEquals("laa66", user.getUsername());
        assertEquals("test", user.getPassword());
        assertEquals(LocalDate.of(1997, 3, 9), user.getBirthDate());
        assertEquals("laa66@laa66.com", user.getEmail());
        assertTrue(user.isEnabled());
        assertFalse(user.getRoles().isEmpty());
    }

    @Test
    @Transactional
    void testGetUserByIdWhenIsNotPresent() {
        User user = userDAO.getUser(-1);
        assertNull(user);
    }

    @Test
    @Transactional
    void testGetUserByUsernameWhenIsPresent() {
        User user = userDAO.getUser("laa66");
        assertEquals(1, user.getId());
        assertEquals("Max", user.getFirstName());
        assertEquals("Newer", user.getLastName());
        assertEquals("laa66", user.getUsername());
        assertEquals("test", user.getPassword());
        assertEquals(LocalDate.of(1997, 3, 9), user.getBirthDate());
        assertEquals("laa66@laa66.com", user.getEmail());
        assertTrue(user.isEnabled());
        assertFalse(user.getRoles().isEmpty());
    }

    @Test
    @Transactional
    void testGetUserByUsernameWhenIsNotPresent() {
        User user = userDAO.getUser("someUsername");
        assertNull(user);
    }


    @Test
    @Transactional
    void testGetUsers() {
        List<User> users = userDAO.getUsers();
        assertEquals(2, users.size());
    }

    @Test
    @Transactional
    void testSaveOrUpdateUser() {
        User user = new User("Max", "Newer", "laa66", "test",
                LocalDate.of(1997, 3, 9), "laa66@laa66.com");
        user.setEnabled(true);
        user.setRoles(Collections.singleton(new Role("ROLE_ADMIN", user)));
        userDAO.saveUser(user);
        User savedUser = userDAO.getUser(3);
        assertNotNull(savedUser);
        assertEquals(user.getRoles().size(), savedUser.getRoles().size());
        assertEquals(user.getUsername(), savedUser.getUsername());
    }

    @Test
    @Transactional
    void testDeleteUser() {
        User user = userDAO.getUser(1);
        assertNotNull(user);
        userDAO.deleteUser(user);
        assertNull(userDAO.getUser(1));
    }

}
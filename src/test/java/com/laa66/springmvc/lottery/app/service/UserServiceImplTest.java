package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.config.SecurityConfig;
import com.laa66.springmvc.lottery.app.config.TestAppConfig;
import com.laa66.springmvc.lottery.app.dao.UserDAO;
import com.laa66.springmvc.lottery.app.entity.Role;
import com.laa66.springmvc.lottery.app.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class, SecurityConfig.class})
@WebAppConfiguration
class UserServiceImplTest {

    @Mock
    private UserDAO userDAOMock;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        when(userDAOMock.getUsers()).thenReturn(List.of(new User(), new User(), new User()));
        assertEquals(3, userService.getUsers().size());
        verify(userDAOMock, times(1)).getUsers();
    }

    @Test
    void testGetUserIfIdExists() {
        User user = new User();
        user.setId(1);
        when(userDAOMock.getUser(1)).thenReturn(user);
        User loadedUser = userService.getUser(1);
        assertNotNull(loadedUser);
        assertEquals(1, loadedUser.getId());
        verify(userDAOMock, times(1)).getUser(1);
    }

    @Test
    void testGetUserIfIdNotExists() {
        when(userDAOMock.getUser(1)).thenReturn(null);
        assertNull(userService.getUser(1));
        verify(userDAOMock, times(1)).getUser(1);
    }

    @Test
    void testLoadUserByUsernameIfUsernameExists() {
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("password");
        user.addRole(new Role("ROLE_ADMIN", user));

        when(userDAOMock.getUser("user")).thenReturn(user);
        UserDetails userDetails = userService.loadUserByUsername("user");
        assertNotNull(userDetails);
        assertEquals("user", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
        verify(userDAOMock, times(1)).getUser("user");
    }

    @Test
    void testLoadUserByUsernameIfUsernameNotExists() {
        when(userDAOMock.getUser("laa66")).thenReturn(null);
        assertNull(userService.loadUserByUsername("laa66"));
        verify(userDAOMock, times(1)).getUser("laa66");
    }

    @Test
    void testLoadRegularUserByUsernameIfUsernameExists() {
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("password");
        user.addRole(new Role("ROLE_ADMIN", user));
        when(userDAOMock.getUser("user")).thenReturn(user);
        User loadedUser = userService.loadRegularUserByUsername("user");
        assertNotNull(loadedUser.getUsername());
        assertEquals("user", loadedUser.getUsername());
        assertEquals("password", loadedUser.getPassword());
        assertTrue(user.getRoles().stream().anyMatch(role -> role.getRole().equals("ROLE_ADMIN")));
        verify(userDAOMock, times(1)).getUser("user");
    }

    @Test
    void testLoadRegularUserByUsernameIfUsernameNotExists() {
        when(userDAOMock.getUser("laa66")).thenReturn(null);
        assertNull(userService.loadRegularUserByUsername("laa66"));
        verify(userDAOMock, times(1)).getUser("laa66");
    }

    @Test
    void testSaveUser() {
        User user = new User();
        doNothing().when(userDAOMock).saveUser(user);
        userService.saveUser(user);
        verify(userDAOMock, times(1)).saveUser(user);
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        doNothing().when(userDAOMock).deleteUser(user);
        userService.deleteUser(user);
        verify(userDAOMock, times(1)).deleteUser(user);
    }

    @Test
    void testMapRolesIfRolesNotNull() {
        User user = new User();
        List<Role> roles = List.of(new Role("ROLE_ADMIN", user), new Role("ROLE_USER", user));

        Object object = ReflectionTestUtils.invokeMethod(userService, "mapRoles", roles);
        Set<String> set = roles.stream().map(Role::getRole).collect(Collectors.toUnmodifiableSet());
        Set<String> returnSet = ((Collection<? extends GrantedAuthority>)object)
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toUnmodifiableSet());

        assertEquals(2, set.size());
        assertTrue(set.containsAll(returnSet));
    }

}
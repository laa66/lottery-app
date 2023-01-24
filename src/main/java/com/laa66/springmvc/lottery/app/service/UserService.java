package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.entity.Ticket;
import com.laa66.springmvc.lottery.app.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    List<User> getUsers();

    User getUser(int id);

    void saveUser(User user);

    void deleteUser(User user);

    void addTicket(int userId, Ticket ticket);

}

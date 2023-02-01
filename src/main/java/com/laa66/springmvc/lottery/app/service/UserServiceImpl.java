package com.laa66.springmvc.lottery.app.service;

import com.laa66.springmvc.lottery.app.dao.UserDAO;
import com.laa66.springmvc.lottery.app.entity.Role;
import com.laa66.springmvc.lottery.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserDAO userDAO;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUser(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRoles(user.getRoles()));
    }

    @Override
    @Transactional
    public User loadRegularUserByUsername(String username) {
        return userDAO.getUser(username);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return userDAO.getUser(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setId(0);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new Role("ROLE_USER", user)));
        userDAO.saveUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }


    // helpers
    private Collection<? extends GrantedAuthority> mapRoles(Collection<Role> collection) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role: collection) authorities.add(new SimpleGrantedAuthority(role.getRole()));
        return authorities;
    }
}

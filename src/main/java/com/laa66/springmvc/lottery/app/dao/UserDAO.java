package com.laa66.springmvc.lottery.app.dao;

import com.laa66.springmvc.lottery.app.entity.User;

import java.util.List;

public interface UserDAO {

    List<User> getUsers();

    User getUser(int id);

    User getUser(String username);

    void saveUser(User user);

    void deleteUser(User user);

}

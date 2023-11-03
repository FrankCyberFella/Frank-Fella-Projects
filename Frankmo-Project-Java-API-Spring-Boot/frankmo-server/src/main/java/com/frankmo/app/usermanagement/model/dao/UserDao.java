package com.frankmo.app.usermanagement.model.dao;

import com.frankmo.app.usermanagement.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User findUserById(int id);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);
}

package com.github.nikitakuchur.webboard.backend.dao;

import com.github.nikitakuchur.webboard.backend.models.User;

import java.util.List;

public interface UserDao {

    void save(User user);

    List<User> findAll();
}

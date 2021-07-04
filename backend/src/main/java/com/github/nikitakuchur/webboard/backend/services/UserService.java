package com.github.nikitakuchur.webboard.backend.services;

import com.github.nikitakuchur.webboard.backend.models.User;

import java.util.List;

public interface UserService {

    void save(User user);

    User getByName(String name);

    List<User> getAll();
}

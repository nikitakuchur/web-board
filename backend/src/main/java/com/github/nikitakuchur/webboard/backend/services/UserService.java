package com.github.nikitakuchur.webboard.backend.services;

import com.github.nikitakuchur.webboard.backend.models.User;

import java.util.List;

/**
 * The user service.
 */
public interface UserService {

    /**
     * Saves the given user.
     *
     * @param user the user
     */
    void save(User user);

    /**
     * Finds the user by the name.
     *
     * @param name the name
     * @return the found user
     */
    User findByName(String name);

    /**
     * Returns all users.
     *
     * @return the list of the users
     */
    List<User> getAll();
}

package com.github.nikitakuchur.webboard.backend.dao;

import com.github.nikitakuchur.webboard.backend.models.User;

import java.util.List;

/**
 * The data access object class for user entity.
 */
public interface UserDao {

    /**
     * Saves the given user.
     *
     * @param user the user
     */
    void save(User user);

    /**
     * Returns all users.
     *
     * @return the list of all users
     */
    List<User> findAll();
}

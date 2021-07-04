package com.github.nikitakuchur.webboard.backend.services;

import com.github.nikitakuchur.webboard.backend.dao.UserDao;
import com.github.nikitakuchur.webboard.backend.models.User;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @EJB
    private UserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findByName(String name) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }
}

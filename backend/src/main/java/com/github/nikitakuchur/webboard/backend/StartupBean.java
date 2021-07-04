package com.github.nikitakuchur.webboard.backend;

import com.github.nikitakuchur.webboard.backend.models.User;
import com.github.nikitakuchur.webboard.backend.services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class StartupBean {

    @Inject
    private UserService userService;

    @PostConstruct
    public void init() {
        // TODO: Delete this later
        if (userService.getAll().stream().anyMatch(user -> user.getName().equals("test"))) {
            return;
        }
        userService.save(new User("test", "1234", "user"));
    }
}

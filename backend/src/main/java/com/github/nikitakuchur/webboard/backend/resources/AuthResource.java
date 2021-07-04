package com.github.nikitakuchur.webboard.backend.resources;

import com.github.nikitakuchur.webboard.backend.models.LoginInfo;
import com.github.nikitakuchur.webboard.backend.models.User;
import com.github.nikitakuchur.webboard.backend.services.UserService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Authentication resource class.
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class AuthResource {

    @Inject
    private UserService userService;

    @Inject
    private Logger logger;

    /**
     * Logs in to the service.
     *
     * @param loginInfo the loginInfo with name and password
     * @param request   the request
     * @return result
     */
    @POST
    @Path("/login")
    public JsonObject login(LoginInfo loginInfo, @Context HttpServletRequest request) {
        try {
            request.login(loginInfo.getName(), loginInfo.getPassword());
            logger.log(Level.INFO, "User with name {0} is logged in.", new Object[]{loginInfo.getName()});
            return Json.createObjectBuilder()
                    .add("result", true)
                    .build();
        } catch (ServletException e) {
            return Json.createObjectBuilder()
                    .add("result", false)
                    .build();
        }
    }

    /**
     * Logs out from the service.
     *
     * @param request the request
     * @return result
     */
    @POST
    @Path("/logout")
    public JsonObject login(@Context HttpServletRequest request) {
        try {
            String name = request.getUserPrincipal().getName();
            request.logout();
            logger.log(Level.INFO, "User with name {0} is logged out.", new Object[]{name});
            return Json.createObjectBuilder()
                    .add("result", true)
                    .build();
        } catch (ServletException e) {
            return Json.createObjectBuilder()
                    .add("result", false)
                    .build();
        }
    }

    @POST
    @Path("/signup")
    public JsonObject signup(LoginInfo loginInfo, @Context HttpServletRequest request) {
        User user = userService.findByName(loginInfo.getName());
        if (user != null) {
            return Json.createObjectBuilder()
                    .add("result", false)
                    .build();
        }
        userService.save(new User(loginInfo.getName(), loginInfo.getPassword(), "user"));
        logger.log(Level.INFO, "User with name {0} is signed up.", new Object[]{loginInfo.getName()});
        return Json.createObjectBuilder()
                .add("result", true)
                .build();
    }
}

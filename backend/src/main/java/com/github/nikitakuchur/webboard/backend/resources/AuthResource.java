package com.github.nikitakuchur.webboard.backend.resources;

import com.github.nikitakuchur.webboard.backend.models.LoginInfo;

import javax.annotation.security.PermitAll;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class AuthResource {

    @POST
    @Path("/login")
    public String login(LoginInfo loginInfo, @Context HttpServletRequest request) {
        try {
            request.login(loginInfo.getName(), loginInfo.getPassword());
            return "{ \"result\": true }";
        } catch (ServletException e) {
            return "{ \"result\": false }";
        }
    }

    @POST
    @Path("/logout")
    public String login(@Context HttpServletRequest request) {
        try {
            request.logout();
            return "{ \"result\": true }";
        } catch (ServletException e) {
            return "{ \"result\": false }";
        }
    }
}

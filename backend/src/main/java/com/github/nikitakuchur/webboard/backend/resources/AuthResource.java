package com.github.nikitakuchur.webboard.backend.resources;

import com.github.nikitakuchur.webboard.backend.models.LoginInfo;

import javax.annotation.security.PermitAll;
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

/**
 * Authentication resource class.
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class AuthResource {

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
            request.logout();
            return Json.createObjectBuilder()
                    .add("result", true)
                    .build();
        } catch (ServletException e) {
            return Json.createObjectBuilder()
                    .add("result", false)
                    .build();
        }
    }
}

package com.github.nikitakuchur.webboard.backend.endpoints.configurator;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * The board endpoint configurator.
 */
public class EndpointSecurityConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        config.getUserProperties().put("authorized", request.isUserInRole("user"));
        config.getUserProperties().put("name", request.getUserPrincipal().getName());
    }
}

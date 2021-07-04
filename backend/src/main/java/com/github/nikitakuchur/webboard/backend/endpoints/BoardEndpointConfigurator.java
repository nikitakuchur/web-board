package com.github.nikitakuchur.webboard.backend.endpoints;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class BoardEndpointConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        config.getUserProperties().put("authorized", request.isUserInRole("user"));
    }
}

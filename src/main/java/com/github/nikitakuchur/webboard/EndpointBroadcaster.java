package com.github.nikitakuchur.webboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.websocket.EncodeException;
import javax.websocket.Session;

@Singleton
public class EndpointBroadcaster {
    private final List<BoardEndpoint> boardEndpoints = new ArrayList<>();

    public void add(BoardEndpoint endpoint) {
        boardEndpoints.add(endpoint);
    }

    public void remove(BoardEndpoint endpoint) {
        boardEndpoints.remove(endpoint);
    }

    public void broadcast(BoardEndpoint sender, BoardMessage message) {
        boardEndpoints.stream()
                .filter(endpoint -> endpoint != sender)
                .forEach(endpoint -> {
                    try {
                        Session session = endpoint.getSession();
                        session.getBasicRemote().sendObject(message);
                    } catch (IOException | EncodeException e) {
                        e.printStackTrace();
                    }
                });
    }
}

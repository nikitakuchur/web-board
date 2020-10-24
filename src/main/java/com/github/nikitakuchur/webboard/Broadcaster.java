package com.github.nikitakuchur.webboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.websocket.EncodeException;
import javax.websocket.Session;

@Singleton
public class Broadcaster {
    private final Map<Integer, List<BoardEndpoint>> boardEndpoints = new HashMap<>();

    public void add(int boardId, BoardEndpoint endpoint) {
        boardEndpoints.computeIfAbsent(boardId, key -> new ArrayList<>()).add(endpoint);
    }

    public void remove(int boardId, BoardEndpoint endpoint) {
        List<BoardEndpoint> endpoints = boardEndpoints.get(boardId);
        if (endpoints != null) {
            endpoints.remove(endpoint);
            if (endpoints.isEmpty()) {
                boardEndpoints.remove(boardId);
            }
        }
    }

    public void broadcast(int boardId, BoardMessage message) {
        boardEndpoints.getOrDefault(boardId, Collections.emptyList())
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

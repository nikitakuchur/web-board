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
    private final Map<Integer, List<Session>> boardSessions = new HashMap<>();

    public void add(int boardId, Session session) {
        boardSessions.computeIfAbsent(boardId, key -> new ArrayList<>()).add(session);
    }

    public void remove(int boardId, Session session) {
        List<Session> sessions = boardSessions.get(boardId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                boardSessions.remove(boardId);
            }
        }
    }

    public void broadcast(int boardId, BoardMessage message) {
        boardSessions.getOrDefault(boardId, Collections.emptyList())
                .forEach(session -> {
                    try {
                        session.getBasicRemote().sendObject(message);
                    } catch (IOException | EncodeException e) {
                        e.printStackTrace();
                    }
                });
    }
}

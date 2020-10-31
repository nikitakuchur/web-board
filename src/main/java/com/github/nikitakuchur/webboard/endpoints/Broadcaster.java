package com.github.nikitakuchur.webboard.endpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import com.github.nikitakuchur.webboard.endpoints.BoardMessage;

/**
 * The broadcaster class contains connected sessions and broadcasts messages.
 */
@Singleton
public class Broadcaster {
    private final Map<Integer, List<Session>> boardSessions = new HashMap<>();

    /**
     * Adds the given session.
     *
     * @param boardId the board id
     * @param session the session
     */
    public void add(int boardId, Session session) {
        boardSessions.computeIfAbsent(boardId, key -> new ArrayList<>()).add(session);
    }

    /**
     * Removes the given session.
     *
     * @param boardId the board id
     * @param session the session
     */
    public void remove(int boardId, Session session) {
        List<Session> sessions = boardSessions.get(boardId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                boardSessions.remove(boardId);
            }
        }
    }

    /**
     * Broadcasts the given message to all sessions connected to the board.
     *
     * @param boardId the board id
     * @param message the message
     */
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

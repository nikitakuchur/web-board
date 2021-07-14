package com.github.nikitakuchur.webboard.backend.endpoints;

import javax.ejb.Singleton;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

/**
 * The group broadcaster class. This entity stores sessions in groups and broadcasts messages between sessions
 * within the same group.
 */
@Singleton
public class GroupBroadcaster {

    private final Map<String, List<Session>> groupSessions = new HashMap<>();

    /**
     * Adds the given session to the group.
     *
     * @param groupId the group id
     * @param session the session
     */
    public void add(String groupId, Session session) {
        groupSessions.computeIfAbsent(groupId, key -> new ArrayList<>()).add(session);
    }

    /**
     * Removes the given session from the group.
     *
     * @param groupId the group id
     * @param session the session
     */
    public void remove(String groupId, Session session) {
        List<Session> sessions = groupSessions.get(groupId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                groupSessions.remove(groupId);
            }
        }
    }

    /**
     * Broadcasts the given message to all sessions within the same group.
     *
     * @param groupId the group id
     * @param message the message
     */
    public void broadcast(String groupId, Object message) {
        groupSessions.getOrDefault(groupId, Collections.emptyList())
                .forEach(session -> {
                    try {
                        session.getBasicRemote().sendObject(message);
                    } catch (IOException | EncodeException e) {
                        e.printStackTrace();
                    }
                });
    }
}

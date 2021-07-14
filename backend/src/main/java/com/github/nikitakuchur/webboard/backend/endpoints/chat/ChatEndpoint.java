package com.github.nikitakuchur.webboard.backend.endpoints.chat;

import com.github.nikitakuchur.webboard.backend.endpoints.GroupBroadcaster;
import com.github.nikitakuchur.webboard.backend.endpoints.configurator.EndpointSecurityConfigurator;
import com.github.nikitakuchur.webboard.backend.services.BoardService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The chat endpoint class.
 */
@ServerEndpoint(value = "/chat-endpoint/{id}", configurator = EndpointSecurityConfigurator.class, decoders = ChatMessageDecoder.class, encoders = ChatMessageEncoder.class)
@Stateless
public class ChatEndpoint {

    private static final String CHAT_PREFIX = "chat_";

    @Inject
    private BoardService boardService;

    @EJB
    private GroupBroadcaster broadcaster;

    @Inject
    private Logger logger;

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) throws IOException {
        Map<String, Object> userProperties = session.getUserProperties();
        Boolean authorized = ((Boolean) userProperties.get("authorized"));
        if (authorized == null || !authorized) {
            handleError(session, "No access");
            session.close();
            return;
        }
        logger.log(Level.INFO, "Opened a new session {0} with board id {1}.", new Object[]{session.getId(), id});
        Integer boardId = validateBoardId(id);
        if (boardId == null) {
            handleError(session, "Board not found");
            session.close();
            return;
        }
        broadcaster.add(CHAT_PREFIX + boardId, session);
        try {
            session.getBasicRemote().sendObject(new ChatMessage("info", "connected"));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
        // TODO: Send previous messages
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("id") String id, ChatMessage message) {
        logger.log(Level.INFO, "Received a new message form the session {0} on the board {1}.",
                new Object[]{session.getId(), id});
        Integer boardId = validateBoardId(id);
        if (boardId == null) {
            handleError(session, "Board not found");
            return;
        }
        String name = (String) session.getUserProperties().get("name");
        message.setUser(name);
        broadcaster.broadcast(CHAT_PREFIX + boardId, message);
        logger.log(Level.INFO, "The message has been broadcast on the board {0}.", new Object[]{boardId});
    }

    private Integer validateBoardId(String id) {
        Integer boardId = parseInteger(id);
        if (boardId == null || boardService.get(boardId) == null) {
            return null;
        }
        return boardId;
    }

    private Integer parseInteger(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("id") String id) {
        broadcaster.remove(CHAT_PREFIX + id, session);
        logger.log(Level.INFO, "The session {0} has been closed.", new Object[]{session.getId()});
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        handleError(session, getStackTrace(throwable));
    }

    private void handleError(Session session, String description) {
        JsonObject errorMessage = Json.createObjectBuilder()
                .add("error", true)
                .add("description", description)
                .build();
        try {
            session.getBasicRemote().sendText(errorMessage.toString());
            session.close();
            logger.log(Level.INFO, "The session {0} got an error \"{1}\".", new Object[]{session.getId(), description});
        } catch (IOException e) {
            logger.log(Level.SEVERE, "an exception was thrown", e);
        }
    }

    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}

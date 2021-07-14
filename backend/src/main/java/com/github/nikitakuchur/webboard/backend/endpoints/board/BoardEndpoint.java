package com.github.nikitakuchur.webboard.backend.endpoints.board;

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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.nikitakuchur.webboard.backend.endpoints.GroupBroadcaster;
import com.github.nikitakuchur.webboard.backend.endpoints.configurator.EndpointSecurityConfigurator;
import com.github.nikitakuchur.webboard.backend.models.Board;
import com.github.nikitakuchur.webboard.backend.models.Stroke;
import com.github.nikitakuchur.webboard.backend.services.BoardService;

/**
 * The board endpoint class.
 */
@ServerEndpoint(value = "/board-endpoint/{id}", configurator = EndpointSecurityConfigurator.class, decoders = BoardMessageDecoder.class, encoders = BoardMessageEncoder.class)
@Stateless
public class BoardEndpoint {

    private static final String BOARD_PREFIX = "board_";

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
        broadcaster.add(BOARD_PREFIX + boardId, session);
        try {
            Board board = boardService.get(boardId);
            session.getBasicRemote().sendObject(BoardMessage.strokesMessage(board.getStrokes()));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("id") String id, BoardMessage message) {
        logger.log(Level.INFO, "Received a new message form the session {0} on the board {1}.",
                new Object[]{session.getId(), id});
        Integer boardId = validateBoardId(id);
        if (boardId == null) {
            handleError(session, "Board not found");
            return;
        }
        if (message.isClear()) {
            boardService.clear(boardId);
        }
        if (message.getStrokes() != null && !message.getStrokes().isEmpty()) {
            Board board = boardService.get(boardId);
            board.addStroke(message.getStrokes().get(0));
            board = boardService.update(board);
            List<Stroke> strokes = board.getStrokes();
            message = BoardMessage.strokesMessage(Collections.singletonList(strokes.get(strokes.size() - 1)));
        }
        if (message.getDeleted() != null) {
            Board board = boardService.get(boardId);
            Stroke stroke = boardService.getStroke(message.getDeleted());
            board.removeStroke(stroke);
            boardService.update(board);
        }
        broadcaster.broadcast(BOARD_PREFIX + boardId, message);
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
        broadcaster.remove(BOARD_PREFIX + id, session);
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

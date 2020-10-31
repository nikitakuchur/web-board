package com.github.nikitakuchur.webboard.endpoints;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.github.nikitakuchur.webboard.models.Board;
import com.github.nikitakuchur.webboard.models.Stroke;
import com.github.nikitakuchur.webboard.services.BoardService;

/**
 * The board endpoint class.
 */
@Stateful
@ServerEndpoint(value = "/api/board-endpoint/{id}", decoders = BoardMessageDecoder.class, encoders = BoardMessageEncoder.class)
public class BoardEndpoint {

    @Inject
    private BoardService boardService;

    @EJB
    private Broadcaster broadcaster;

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        Integer boardId = validateBoardId(id);
        if (boardId == null) {
            handleError(session, "Board not found");
            return;
        }
        broadcaster.add(boardId, session);
        try {
            Board board = boardService.get(boardId);
            session.getBasicRemote().sendObject(BoardMessage.strokesMessage(board.getStrokes()));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("id") String id, BoardMessage message) {
        Integer boardId = validateBoardId(id);
        if (boardId == null) {
            handleError(session, "Board not found");
            return;
        }
        if (message.isClear()) {
            Board board = boardService.get(boardId);
            boardService.clear(board);
        }
        if (message.getStrokes() != null && !message.getStrokes().isEmpty()) {
            Board board = boardService.get(boardId);
            boardService.addStroke(board, message.getStrokes().get(0));
        }
        if (message.getDeleted() != null) {
            Stroke stroke = boardService.getStroke(message.getDeleted());
            boardService.deleteStroke(stroke);
        }
        broadcaster.broadcast(boardId, message);
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
    public void onClose(Session session, @PathParam("id") int id) {
        broadcaster.remove(id, session);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}

package com.github.nikitakuchur.webboard;

import javax.ejb.EJB;
import javax.ejb.Stateful;
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

@Stateful
@ServerEndpoint(value = "/api/board-endpoint/{id}", decoders = BoardMessageDecoder.class, encoders = BoardMessageEncoder.class)
public class BoardEndpoint {
    @EJB
    private BoardService boardService;
    @EJB
    private Broadcaster broadcaster;

    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        this.session = session;
        Integer boardId = parseId(id);
        if (boardId == null || boardService.get(boardId) == null) {
            handleError("Board not found");
            return;
        }
        broadcaster.add(boardId, this);
        try {
            Board board = boardService.get(boardId);
            session.getBasicRemote().sendObject(BoardMessage.strokesMessage(board.getStrokes()));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    private Integer parseId(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("id") int id, BoardMessage message) {
        if (message.isClear()) {
            Board board = boardService.get(id);
            boardService.clear(board);
        }
        if (!message.getStrokes().isEmpty()) {
            Board board = boardService.get(id);
            boardService.addStrokes(board, message.getStrokes());
        }
        if (message.getDeleted() != null) {
            Stroke stroke = boardService.getStroke(message.getDeleted());
            boardService.deleteStroke(stroke);
        }
        broadcaster.broadcast(id, message);
    }

    @OnClose
    public void onClose(Session session, @PathParam("id") int id) {
        broadcaster.remove(id, this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        handleError(getStackTrace(throwable));
    }

    private void handleError(String description) {
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

    public Session getSession() {
        return session;
    }
}

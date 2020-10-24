package com.github.nikitakuchur.webboard;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

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
    public void onOpen(Session session, @PathParam("id") int id) {
        this.session = session;
        if (boardService.get(id) == null) {
            onError(session, null);
            return;
        }
        broadcaster.add(id, this);
        try {
            Board board = boardService.get(id);
            session.getBasicRemote().sendObject(BoardMessage.strokesMessage(board.getStrokes()));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
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
        if (message.getDeleted() != -1) {
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
        try {
            session.getBasicRemote().sendText("{ \"error\": true, \"description\": \"Board not found\" }");
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }
}

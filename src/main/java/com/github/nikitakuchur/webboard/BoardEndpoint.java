package com.github.nikitakuchur.webboard;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.*;

@Stateful
@ServerEndpoint(value = "/board-endpoint", decoders = BoardMessageDecoder.class, encoders = BoardMessageEncoder.class)
public class BoardEndpoint {

    @EJB
    private Board board;

    private Session session;
    private static final List<BoardEndpoint> boardEndpoints = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        boardEndpoints.add(this);
        try {
            session.getBasicRemote().sendObject(BoardMessage.strokesMessage(board.getStrokes()));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, BoardMessage message) {
        if (message.isClear()) {
            board.clear();
        }
        if (!message.getStrokes().isEmpty()) {
            board.addStrokes(message.getStrokes());
        }
        if (message.getDeleted() != -1) {
            board.removeStroke(message.getDeleted());
        }
        broadcast(message);
    }

    private void broadcast(BoardMessage message) {
        boardEndpoints.stream()
                .filter(endpoint -> endpoint != this)
                .forEach(endpoint -> {
                    try {
                        endpoint.session.getBasicRemote().sendObject(message);
                    } catch (IOException | EncodeException e) {
                        e.printStackTrace();
                    }
                });
    }

    @OnClose
    public void onClose(Session session) {
        boardEndpoints.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}

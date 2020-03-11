package com.github.nikitakuchur.webboard;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(value = "/board-endpoint", decoders = BoardMessageDecoder.class, encoders = BoardMessageEncoder.class)
public class BoardEndpoint {

    private Session session;
    private static final List<BoardEndpoint> boardEndpoints = new ArrayList<>();
    private static final List<Stroke> strokes = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        boardEndpoints.add(this);
        try {
            session.getBasicRemote().sendObject(BoardMessage.strokesMessage(strokes));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, BoardMessage message) {
        if (message.isClear()) {
            strokes.clear();
        }
        List<Stroke> newStrokes = message.getStrokes();
        if (newStrokes != null) {
            strokes.addAll(newStrokes);
        }
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) {
        boardEndpoints.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
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
}

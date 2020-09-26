package com.github.nikitakuchur.webboard;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

@Stateful
@ServerEndpoint(value = "/board-endpoint", decoders = BoardMessageDecoder.class, encoders = BoardMessageEncoder.class)
public class BoardEndpoint {
    @EJB
    private BoardService boardService;
    @EJB
    private Broadcaster broadcaster;

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        broadcaster.add(this);
        try {
            session.getBasicRemote().sendObject(BoardMessage.strokesMessage(boardService.getStrokes()));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, BoardMessage message) {
        if (message.isClear()) {
            boardService.clear();
        }
        if (!message.getStrokes().isEmpty()) {
            boardService.addStrokes(message.getStrokes());
        }
        if (message.getDeleted() != -1) {
            boardService.removeStroke(message.getDeleted());
        }
        broadcaster.broadcast(this, message);
    }

    @OnClose
    public void onClose(Session session) {
        broadcaster.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    public Session getSession() {
        return session;
    }
}

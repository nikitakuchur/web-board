package com.github.nikitakuchur.webboard.backend.endpoints;

import com.github.nikitakuchur.webboard.backend.models.Point;
import com.github.nikitakuchur.webboard.backend.models.Stroke;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * The board message decoder.
 */
public class BoardMessageDecoder implements Decoder.Text<BoardMessage> {

    private static final Jsonb jsonb = JsonbBuilder.create();

    @Override
    public BoardMessage decode(String s) {
        BoardMessage message = jsonb.fromJson(s, BoardMessage.class);
        for (Stroke stroke : message.getStrokes()) {
            for (Point point : stroke.getPoints()) {
                point.setStroke(stroke);
            }
        }
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}

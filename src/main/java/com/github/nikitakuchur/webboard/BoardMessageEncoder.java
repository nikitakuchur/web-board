package com.github.nikitakuchur.webboard;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * The board message encoder.
 */
public class BoardMessageEncoder implements Encoder.Text<BoardMessage> {

    private static final Jsonb jsonb = JsonbBuilder.create();

    @Override
    public String encode(BoardMessage message) {
        return jsonb.toJson(message);
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

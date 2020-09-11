package com.github.nikitakuchur.webboard;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class BoardMessageDecoder implements Decoder.Text<BoardMessage> {

    private static final Jsonb jsonb = JsonbBuilder.create();

    @Override
    public BoardMessage decode(String s) {
        return jsonb.fromJson(s, BoardMessage.class);
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

package com.github.nikitakuchur.webboard.backend.endpoints.chat;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {

    private static final Jsonb jsonb = JsonbBuilder.create();

    @Override
    public String encode(ChatMessage message) {
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

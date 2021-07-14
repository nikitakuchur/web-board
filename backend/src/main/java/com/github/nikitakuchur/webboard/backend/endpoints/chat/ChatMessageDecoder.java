package com.github.nikitakuchur.webboard.backend.endpoints.chat;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {

    private static final Jsonb jsonb = JsonbBuilder.create();

    @Override
    public ChatMessage decode(String s) {
        return jsonb.fromJson(s, ChatMessage.class);
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

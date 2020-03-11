package com.github.nikitakuchur.webboard;

import com.google.gson.Gson;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class BoardMessageDecoder implements Decoder.Text<BoardMessage> {

    private static final Gson gson = new Gson();

    @Override
    public BoardMessage decode(String s) {
        return gson.fromJson(s, BoardMessage.class);
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

package com.github.nikitakuchur.webboard;

import com.google.gson.Gson;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class BoardMessageEncoder implements Encoder.Text<BoardMessage>  {

    private static final Gson gson = new Gson();

    @Override
    public String encode(BoardMessage message) {
        return gson.toJson(message);
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

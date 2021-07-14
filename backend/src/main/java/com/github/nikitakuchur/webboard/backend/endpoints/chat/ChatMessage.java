package com.github.nikitakuchur.webboard.backend.endpoints.chat;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ChatMessage {
    private String user;
    private String text;

    @JsonbCreator
    public ChatMessage(@JsonbProperty("user") String user, @JsonbProperty("text") String text) {
        this.user = user;
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

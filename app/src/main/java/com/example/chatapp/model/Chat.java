package com.example.chatapp.model;

public class Chat {

    private String username;
    private String message;

    public Chat() {

    }

    public Chat(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}

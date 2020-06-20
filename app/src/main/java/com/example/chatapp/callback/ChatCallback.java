package com.example.chatapp.callback;

import com.example.chatapp.model.Chat;

import java.util.ArrayList;

public interface ChatCallback {
    void finish(ArrayList<Chat> chats);
}

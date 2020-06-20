package com.example.chatapp.callback;

import com.example.chatapp.model.User;

public interface UserCallback {
    void finish(User user);
    void error();
}

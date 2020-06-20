package com.example.chatapp.manager;

import androidx.annotation.NonNull;

import com.example.chatapp.callback.ChatCallback;
import com.example.chatapp.model.Chat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatDataManager {

    private DatabaseReference database;

    private ChatDataManager() {
        database = FirebaseDatabase.getInstance().getReference("chats");
    }

    private static ChatDataManager instance = new ChatDataManager();

    public static ChatDataManager getInstance() {
        return instance;
    }

    public void addChat(Chat chat) {
        String id = database.push().getKey();
        if (id != null) {
            database.child(id).setValue(chat);
        }
    }

    public void getChats(final ChatCallback callback) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Chat> chats = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Chat chat = postSnapshot.getValue(Chat.class);
                    if (chat != null && chat.getUsername() != null) {
                        chats.add(chat);
                    }
                }
                callback.finish(chats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

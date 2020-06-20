package com.example.chatapp.manager;

import androidx.annotation.NonNull;

import com.example.chatapp.callback.UserCallback;
import com.example.chatapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDataManager {

    private DatabaseReference database;
    private ValueEventListener eventListener;
    private User user;

    private UserDataManager() {
        database = FirebaseDatabase.getInstance().getReference("users");
    }

    private static UserDataManager instance = new UserDataManager();

    public static UserDataManager getInstance() {
        if (instance == null) {
            instance = new UserDataManager();
        }
        return instance;
    }

    public void login(final User _user, final UserCallback callback) {
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    if (user != null && user.getUsername().equals(_user.getUsername()) && user.getPassword().equals(_user.getPassword())) {
                        callback.finish(user);
                        return;
                    }
                }
                callback.error();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.error();
            }
        };
        database.addValueEventListener(eventListener);
    }

    public void signUp(User user) {
        String id = database.push().getKey();
        if (id != null) {
            database.child(id).setValue(user);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void removeValueEventListener() {
        if (database != null && eventListener != null) {
            database.removeEventListener(eventListener);
        }
    }
}

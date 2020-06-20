package com.example.chatapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.adapter.ChatAdapter;
import com.example.chatapp.callback.ChatCallback;
import com.example.chatapp.manager.ChatDataManager;
import com.example.chatapp.manager.UserDataManager;
import com.example.chatapp.model.Chat;

import java.util.ArrayList;

public class ChatActivity extends CommonActivity implements View.OnClickListener {

    private RecyclerView chatList;
    private ChatAdapter adapter;
    private EditText tvMessage;

    private ArrayList<Chat> chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chats           = new ArrayList<>();
        tvMessage       = findViewById(R.id.tv_message);
        chatList        = findViewById(R.id.chat_list);
        Button btnSend  = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(onClickListener);

        setRecyclerViewAdapter();

        ChatDataManager.getInstance().getChats(new ChatCallback() {
            @Override
            public void finish(ArrayList<Chat> chats) {
                ChatActivity.this.chats = chats;
                adapter.setChatList(chats);
                adapter.notifyDataSetChanged();
                // scroll to latest chat
                chatList.scrollToPosition(chats.size()-1);
            }
        });
    }

    private void setRecyclerViewAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ChatAdapter();
        adapter.setChatList(chats);
        chatList.setLayoutManager(layoutManager);
        chatList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // it's not in specs to validate if empty but it's better prevent empty message right?
            if (!tvMessage.getText().toString().trim().isEmpty()) {
                Chat chat = new Chat(UserDataManager.getInstance().getUser().getUsername(), tvMessage.getText().toString());
                ChatDataManager.getInstance().addChat(chat);
                tvMessage.setText("");
            }
        }
    };
}

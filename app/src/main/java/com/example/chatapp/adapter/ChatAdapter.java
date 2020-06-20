package com.example.chatapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.manager.UserDataManager;
import com.example.chatapp.model.Chat;

import java.util.ArrayList;

public class ChatAdapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Chat> chatList;
    private final int VIEW_TYPE_OWN     = 1;
    private final int VIEW_TYPE_OTHER   = 2;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username, tvMessage;

        public ViewHolder(View view) {
            super(view);

            username    = view.findViewById(R.id.tv_username);
            tvMessage   = view.findViewById(R.id.tv_message);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = viewType == VIEW_TYPE_OWN ? R.layout.chat_item : R.layout.chat_item_other;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat message = chatList.get(position);
        boolean isOther = !message.getUsername().equals(UserDataManager.getInstance().getUser().getUsername());
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.username.setText(isOther ? message.getUsername() : "You");
        viewHolder.tvMessage.setText(message.getMessage());
    }

    public void setChatList(ArrayList<Chat> chatList) {
        this.chatList = chatList;
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = chatList.get(position);
        return chat.getUsername().equals(UserDataManager.getInstance().getUser().getUsername()) ? VIEW_TYPE_OWN : VIEW_TYPE_OTHER;
    }
}
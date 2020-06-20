package com.example.chatapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.chatapp.R;
import com.example.chatapp.manager.UserDataManager;

public class CommonActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(getLayoutInflater().inflate(R.layout.title_bar, null),
                    new ActionBar.LayoutParams(
                            ActionBar.LayoutParams.WRAP_CONTENT,
                            ActionBar.LayoutParams.MATCH_PARENT,
                            Gravity.CENTER
                    )
            );
            TextView tvTitle = findViewById(R.id.tv_title);
            TextView btnLogout = findViewById(R.id.btn_logout);
            tvTitle.setText(getTitle());
            actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg_border_bottom));
            btnLogout.setVisibility(UserDataManager.getInstance().getUser() != null ? View.VISIBLE : View.GONE);
            btnLogout.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        UserDataManager.getInstance().setUser(null);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("destination", "sign_up");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

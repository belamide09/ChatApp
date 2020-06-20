package com.example.chatapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chatapp.callback.UserCallback;
import com.example.chatapp.R;
import com.example.chatapp.manager.UserDataManager;
import com.example.chatapp.model.User;

public class AccountSubmitActivity extends CommonActivity implements View.OnClickListener {

    private RelativeLayout loader;
    private EditText tvUsername, tvPassword;
    private TextView tvErrorUsername, tvErrorPassword, tvSignUp, tvLogin;
    private Button btnSignUp, btnLogin;
    private String action;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_submit);

        action              = getIntent().getStringExtra("action");
        tvUsername          = findViewById(R.id.tv_username);
        tvPassword          = findViewById(R.id.tv_password);
        tvErrorUsername     = findViewById(R.id.username_incorrect);
        tvErrorPassword     = findViewById(R.id.password_incorrect);
        loader              = findViewById(R.id.loader);
        btnSignUp           = findViewById(R.id.btn_signup);
        btnLogin            = findViewById(R.id.btn_login);
        tvSignUp            = findViewById(R.id.tv_signup);
        tvLogin             = findViewById(R.id.tv_login);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);

        init();
    }

    private void init() {
        if (action == null) {
            return;
        }

        switch (action) {
            case "sign_up":
                btnSignUp.setVisibility(View.VISIBLE);
                tvSignUp.setVisibility(View.GONE);
                tvLogin.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
                break;
            case "login":
                btnSignUp.setVisibility(View.GONE);
                tvSignUp.setVisibility(View.VISIBLE);
                tvLogin.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
        }

        tvUsername.setText("");
        tvPassword.setText("");
        tvErrorUsername.setVisibility(View.GONE);
        tvErrorPassword.setVisibility(View.GONE);
        UserDataManager.getInstance().removeValueEventListener();
    }

    @Override
    public void onClick(View view) {
        String username = tvUsername.getText().toString().trim();
        String password = tvPassword.getText().toString().trim();

        switch (view.getId()) {
            case R.id.btn_login:
                if (validateInput()) {
                    user = new User(username, password);
                    loader.setVisibility(View.VISIBLE);
                    UserDataManager.getInstance().login(user, new UserCallback() {
                        @Override
                        public void finish(User _user) {
                            if (action.equals("login")) {
                                UserDataManager.getInstance().setUser(user);
                                Intent intent = new Intent(AccountSubmitActivity.this, ChatActivity.class);
                                startActivity(intent);
                                loader.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void error() {
                            tvErrorUsername.setVisibility(View.VISIBLE);
                            tvErrorPassword.setVisibility(View.VISIBLE);
                            loader.setVisibility(View.GONE);
                        }
                    });
                } else {
                    tvErrorUsername.setVisibility(View.VISIBLE);
                    tvErrorPassword.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_signup:
                if (validateInput()) {
                    user = new User(username, password);
                    UserDataManager.getInstance().setUser(user);
                    UserDataManager.getInstance().signUp(user);
                    Intent intent = new Intent(AccountSubmitActivity.this, ChatActivity.class);
                    startActivity(intent);
                } else {
                    tvErrorUsername.setVisibility(View.VISIBLE);
                    tvErrorPassword.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_login:
                action = "login";
                init();
                break;
            case R.id.tv_signup:
                action = "sign_up";
                init();
        }
    }

    private boolean validateInput() {
        String username = tvUsername.getText().toString().trim();
        String password = tvPassword.getText().toString().trim();
        return username.length() >= 8 && username.length() <= 16 && password.length() >= 8 && password.length() <= 16;
    }
}

package com.example.chatapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chatapp.R;

import java.util.Objects;

public class MainActivity extends CommonActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSignUp    = findViewById(R.id.btn_signup);
        Button btnLogin     = findViewById(R.id.btn_login);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        if (getIntent() != null && Objects.equals(getIntent().getStringExtra("destination"), "sign_up")) {
            Intent intent = new Intent(MainActivity.this, AccountSubmitActivity.class);
            intent.putExtra("action", "sign_up");
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, AccountSubmitActivity.class);
        switch (view.getId()) {
            case R.id.btn_signup:
                intent.putExtra("action", "sign_up");
                break;
            case R.id.btn_login:
                intent.putExtra("action", "login");
        }
        startActivity(intent);
    }


    // prevent going to home when back pressed
    @Override
    public void onBackPressed() { }
}

package com.example.windows.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartingActivity extends AppCompatActivity
{
    private Button btn_login, btn_reg;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        mAuth = FirebaseAuth.getInstance();
        btn_login = findViewById(R.id.BTN_LOGIN);
        btn_reg = findViewById(R.id.BTN_CREATEACCOUNT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            startNextPage(MainActivity.class);
            finish();
        }
    }

    public void loginClicked(View view)
    {
        startNextPage(LoginActivity.class);
    }
    public void registerClicked(View view)
    {
        startNextPage(RegisterActivity.class);
    }

    public void startNextPage(Class<?> destination)
    {
        Intent nextPage = new Intent(StartingActivity.this, destination);
        startActivity(nextPage);
    }
}

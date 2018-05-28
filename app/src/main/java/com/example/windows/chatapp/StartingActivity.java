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
            Intent mainActivityPage = new Intent(StartingActivity.this, MainActivity.class);
            startActivity(mainActivityPage);
            finish();
        }
    }

    public void loginClicked(View view)
    {

    }
    public void registerClicked(View view)
    {
        Intent startRegisterPage = new Intent(StartingActivity.this, RegisterActivity.class);
        startActivity(startRegisterPage);
    }
}

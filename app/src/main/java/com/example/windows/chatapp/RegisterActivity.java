package com.example.windows.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity
{
    private TextInputEditText displayName, emailAdd, password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        displayName = findViewById(R.id.ET_DISPLAYNAME);
        emailAdd = findViewById(R.id.ET_EMAIL);
        password = findViewById(R.id.ET_PASSWORD);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            sendtoMainPage();
        }
    }

    public void registerBtnClicked(View view)
    {
        if(validetRegisterEditText(displayName.getText().toString().trim(), emailAdd.getText().toString().trim(), password.getText().toString().trim()))
        {
            registerUser(emailAdd.getText().toString().trim(), password.getText().toString().trim());
        }
        else
        {
            Toast.makeText(RegisterActivity.this, "Don't leave blank spaces", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean registerUser(String email, String pword)
    {
        mAuth.createUserWithEmailAndPassword(email, pword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            sendtoMainPage();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(RegisterActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        return true;
    }
    public void sendtoMainPage()
    {
        Intent sendtoMain = new Intent(RegisterActivity.this, MainActivity.class);
        sendtoMain.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(sendtoMain);
        finish();
    }

    public boolean validetRegisterEditText(String displayName, String email, String password)
    {
        if(displayName.isEmpty() || email.isEmpty() || password.isEmpty())
        {
            return false;
        }
        return true;
    }
}

package com.example.windows.chatapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends Activity
{
    private TextInputEditText email,password;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.ET_EMAIL_LOGIN);
        password = findViewById(R.id.ET_PASSWORD_LOGIN);
    }

    public void submitClicked(View view)
    {
        if(validateEditText(email.getText().toString().trim(), password.getText().toString().trim()))
        {
            login(email.getText().toString().trim(), password.getText().toString().trim());
        }
        else
        {
            Toast.makeText(LoginActivity.this, "Don't leave blank spaces", Toast.LENGTH_SHORT).show();
        }
    }
    public void login(String user_email, String user_password)
    {
        hideKeyboard();
        showProgress("SHOW");
        mAuth.signInWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            showProgress("HIDE");
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            nextPage(MainActivity.class);
                            finish();
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            showProgress("HIDE");
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void nextPage(Class<?> destination)
    {
        Intent next = new Intent(LoginActivity.this, destination);
        startActivity(next);
        finish();
    }

    public boolean validateEditText(String emailSize, String passwordSize)
    {
        if(emailSize.isEmpty() || passwordSize.isEmpty())
        {
            return false;
        }
        return true;
    }
    public void hideKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(LoginActivity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showProgress(String state)
    {
        if(state == "SHOW")
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }
        else if(state == "HIDE")
        {
            progressDialog.dismiss();
        }
    }
}

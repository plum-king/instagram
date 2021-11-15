package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextInputEditText inputId, inputPassword;
    private Button login_btn, signup_btn;

    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        signup_btn = findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(this);
        
        inputId = findViewById(R.id.InputLoginId);
        inputPassword = findViewById(R.id.InputLoginPw);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_btn:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
            case R.id.login_btn:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String id = inputId.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if(id.isEmpty()) {
            inputId.setError("Email is Required");
            inputId.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(id).matches()) {
            inputId.setError("Id is Email");
            inputId.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            inputPassword.setError("Email is Required");
            inputPassword.requestFocus();
            return;
        }
        if(password.length() < 6) {
            inputPassword.setError("Password is over 5");
            inputPassword.requestFocus();
            return;
        }

        progressDialog.setMessage("Welcome to Instagram...");
        progressDialog.setTitle("Login");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(id, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                progressDialog.dismiss();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                return;
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Failed to Login", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
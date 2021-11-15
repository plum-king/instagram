package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextInputEditText login_id, login_password;
    private Button login_btn, signup_btn;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        signup_btn = findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(this);
        
        login_id = findViewById(R.id.login_id);
        login_password = findViewById(R.id.login_password);
        
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
        String id = login_id.getText().toString().trim();
        String password = login_id.getText().toString().trim();

        if(id.isEmpty()) {
            login_id.setError("Email is Required");
            login_id.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            login_password.setError("Email is Required");
            login_password.requestFocus();
            return;
        }
    }

}
/*
 * Layout: Create Account
 * Feature:  파이어베이스 데이터베이스에 회원가입 정보 저장.
 *           사용되는 액티비티의 id 머릿말 create_
 * */
package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;

    private EditText create_email, create_nickname, create_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.create_btn).setOnClickListener(this);
        findViewById(R.id.login_btn).setOnClickListener(this);

        create_email = findViewById(R.id.create_email);
        create_nickname = findViewById(R.id.create_nickname);
        create_password = findViewById(R.id.create_password);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_btn:
                signUpUser();
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.login_btn:
                Intent i2 = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i2);
                break;
        }
    }
    private void signUpUser() {
        String email = create_email.getText().toString().trim();
        String nickname = create_nickname.getText().toString().trim();
        String password = create_password.getText().toString().trim();

        if(email.isEmpty()) {
            create_email.setError("Email is Required");
            create_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            create_email.setError("Invalidate Email");
            create_email.requestFocus();
            return;
        }

        if(nickname.isEmpty()) {
            create_nickname.setError("NickName is Required");
            create_nickname.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            create_password.setError("Password is Required");
            create_password.requestFocus();
            return;
        }
        if(password.length() < 8) {
            create_password.setError("Min length is 8");
            create_password.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success
                    SignUpUser user = new SignUpUser(email, nickname, password);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Welcome to Instagram",
                                        Toast.LENGTH_SHORT);
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, "Try Again",
                                        Toast.LENGTH_SHORT);
                            }
                        }
                    });
                } else {
                    // If sign in fails
                    Toast.makeText(SignUpActivity.this, "Try Again",
                            Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
/*
 * Layout: Create Account
 * Feature:  파이어베이스 데이터베이스에 회원가입 정보 저장
 */
package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SignUpActivity";
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    TextInputEditText inputEmail, inputUserId, inputPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        findViewById(R.id.create_btn).setOnClickListener(this);
        findViewById(R.id.login_btn).setOnClickListener(this);

        inputEmail = findViewById(R.id.InputEmail);
        inputUserId = findViewById(R.id.InputUserId);
        inputPassword = findViewById(R.id.InputPassword);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View view) {
        Intent i= new Intent(SignUpActivity.this, LoginActivity.class);;
        switch (view.getId()) {
            case R.id.create_btn:
                signUpUser();
                startActivity(i);
                break;
            case R.id.login_btn:
                startActivity(i);
                break;
        }
    }
    private void signUpUser() {
        String email = inputEmail.getText().toString();
        String userid = inputUserId.getText().toString();
        String password = inputPassword.getText().toString();

        if(email.isEmpty()) {
            inputEmail.setError("Email is Required");
            inputEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Invalidate Email");
            inputEmail.requestFocus();
            return;
        }

        if(userid.isEmpty()) {
            inputUserId.setError("NickName is Required");
            inputUserId.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            inputPassword.setError("Password is Required");
            inputPassword.requestFocus();
            return;
        }
        if(password.length() < 6) {
            inputPassword.setError("Min length is 8");
            inputPassword.requestFocus();
            return;
        }
        progressDialog.setMessage("Please wait while Registration...");
        progressDialog.setTitle("Registration");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    // Sign in success
                    SignUpUser user = new SignUpUser(email, userid, password);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Welcome to Instagram",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, "Try Again",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // If sign in fails
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Try Again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
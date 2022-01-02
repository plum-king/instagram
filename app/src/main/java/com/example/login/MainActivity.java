/*
 * Layout: HomeActivity
 * Feature: 인스타그램 메인 화면
 */
package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    public FirebaseDatabase database;
    ImageButton plusBtn;
    Button tempBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plusBtn = (ImageButton)findViewById(R.id.plus_imgBtn);
        //tempBtn = (Button)findViewById(R.id.temp_btn);
        plusBtn.setOnClickListener(this);
        //tempBtn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = database.getInstance().getReference();

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plus_imgBtn:
                startActivity(new Intent(MainActivity.this, NewFeedActivity.class));
                break;
//            case R.id.temp_btn:
//                startActivity(new Intent(MainActivity.this, NewFeedActivity.class));
//                break;
        }
    }
}
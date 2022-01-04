/*
 * Layout: HomeActivity
 * Feature: 인스타그램 메인 화면
 */
package com.example.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.Adapter.PostAdapter;
import com.example.Adapter.StoryAdapter;
import com.example.Model.Post;
import com.example.Model.Story;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView storyRecycler;
    StoryAdapter storyAdapter;

    RecyclerView postRecycler;
    PostAdapter postAdapter;

    ImageButton top_add, top_heart, top_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bottom Navigation
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnItemSelectedListener(item-> {
                item.getItemId();
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_mypage:
                        intent = new Intent(MainActivity.this, EditProfileActivity.class);
                        startActivity(intent);
                        break;
                } return true;
            });

        //StoryRecycler
        storyRecycler = findViewById(R.id.storyRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);
        storyRecycler.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Story> options =
                new FirebaseRecyclerOptions.Builder<Story>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("ShortInfo"), Story.class)
                .build();

        storyAdapter = new StoryAdapter(options);
        storyRecycler.setAdapter(storyAdapter);

        //PostRecycler
        postRecycler = findViewById(R.id.postRecycler);
        postRecycler.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Post> pOptions =
                new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Post").orderByChild("timestamp"), Post.class)
                .build();
        postAdapter = new PostAdapter(pOptions);
        postRecycler.setAdapter(postAdapter);

        //Top Button
        top_add = findViewById(R.id.top_add);
        top_heart = findViewById(R.id.top_heart);
        top_send = findViewById(R.id.top_send);

        top_add.setOnClickListener(this);
        top_heart.setOnClickListener(this);
        top_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_add:
                startActivity(new Intent(MainActivity.this, NewFeedActivity.class));
                break;
            //다른 화면 추가
            case R.id.top_heart:
                Toast.makeText(this, "Heart Activity", Toast.LENGTH_SHORT);
                break;
            case R.id.top_send:
                Toast.makeText(this, "Send Activity", Toast.LENGTH_SHORT);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        storyAdapter.startListening();
        postAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        storyAdapter.stopListening();
        postAdapter.stopListening();
    }
}
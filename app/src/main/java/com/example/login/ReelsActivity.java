package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReelsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reels);
        //final VideoView videoview=(VideoView)findViewById(R.id.videoView);
        //Video View에서 보여줄 동영상주소.
        //Uri url= Uri.parse("https://www.youtube.com/watch?v=IbiA2LATo0k");
        //videoview.setVideoURI(url);
        //비디오 컨트롤바.
        //videoview.setMediaController(new MediaController(this));
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnItemSelectedListener(item-> {
            item.getItemId();
            Intent intent;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    intent = new Intent(ReelsActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_reels:
                    intent = new Intent(ReelsActivity.this, ReelsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_search:
                    intent = new Intent(ReelsActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_shop:
                    intent = new Intent(ReelsActivity.this, ShopActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_mypage:
                    intent = new Intent(ReelsActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    break;
            } return true;
        });
    }
}

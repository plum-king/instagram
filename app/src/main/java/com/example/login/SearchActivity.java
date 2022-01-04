package com.example.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends MainActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnItemSelectedListener(item-> {
            item.getItemId();
            Intent intent;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    intent = new Intent(SearchActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_reels:
                    intent = new Intent(SearchActivity.this, ReelsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_search:
                    intent = new Intent(SearchActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_shop:
                    intent = new Intent(SearchActivity.this, ShopActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_mypage:
                    intent = new Intent(SearchActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    break;
            } return true;
        });
    }
}

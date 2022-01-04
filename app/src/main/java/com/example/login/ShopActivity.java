package com.example.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShopActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnItemSelectedListener(item-> {
            item.getItemId();
            Intent intent;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    intent = new Intent(ShopActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_reels:
                    intent = new Intent(ShopActivity.this, ReelsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_search:
                    intent = new Intent(ShopActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_shop:
                    intent = new Intent(ShopActivity.this, ShopActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_mypage:
                    intent = new Intent(ShopActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    break;
            } return true;
        });
    }
}

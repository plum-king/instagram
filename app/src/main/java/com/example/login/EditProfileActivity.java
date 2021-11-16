package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity {

    ImageView close, done, profile_pic;
    TextView edit_pic;
    EditText name, fullname, web, bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        close = (ImageView) findViewById(R.id.close);
        done = (ImageView) findViewById(R.id.done);
        profile_pic = (ImageView) findViewById(R.id.profile_pic);
        edit_pic = (TextView) findViewById(R.id.edit_pic);
        name = (EditText) findViewById(R.id.edit_name);
        fullname = (EditText) findViewById(R.id.edit_fullname);
        web = (EditText) findViewById(R.id.edit_website);
        bio = (EditText) findViewById(R.id.edit_bio);






        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
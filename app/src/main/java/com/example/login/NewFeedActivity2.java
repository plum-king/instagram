package com.example.login;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NewFeedActivity2 extends NewFeedActivity implements View.OnClickListener{

    EditText editText;
    String content; // 게시글

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Button button1=findViewById(R.id.back);
        button1.setOnClickListener(this);
        Button button2=findViewById(R.id.doneFeed);
        button2.setOnClickListener(this);
        editText = findViewById(R.id.contents);
        editText.setOnClickListener(this);
    }

    public void UploadText(){
        content = editText.getText().toString();
        Map<String, Object> uploadref = new HashMap<>();
        uploadref.put("feed", content);
        up.setText(content);
        databaseReference.child("Users").child(uid).push()
            .setValue(up.getText()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"저장을 완료했습니다", Toast.LENGTH_LONG).show(); //얘만 뜨긴 하는데 그냥 바로 함수 만들어서 그런듯
            }
            public void onFailure(Void aVoid){
                Toast.makeText(getApplicationContext(),"저장을 실패했습니다", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.doneFeed:
                UploadImage(); //이미지랑 텍스트가 안올라감
                UploadText();
                break;
        }
    }
}

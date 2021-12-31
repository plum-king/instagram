package com.example.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

public class NewFeedActivity extends MainActivity implements View.OnClickListener {
    public static final String TAG = "NewFeedActivity";

    //카메라 기능 없는 코드
    ImageView imageView;
    Button buttonImg;
    Button upTest;
    EditText editText;
    String content; // 게시글

    public FirebaseAuth mAuth;
    FirebaseUser mUser;
    public FirebaseDatabase database;
    public FirebaseStorage storage;
    String uid;

    // Create a storage reference from our app
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    int Image_Request_Code = 7;
    Uri FilePathUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        //Auth, DB, Storage 초기화
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();     //현재 로그인한 유저
        uid = mUser.getUid();

        storageReference = storage.getInstance().getReference();
        databaseReference = database.getInstance().getReference();
//        String num = null;
//        String filename = "feed" + num + ".jpg";

        progressDialog = new ProgressDialog(NewFeedActivity.this);
        imageView = (ImageView)findViewById(R.id.main_image);
        editText = findViewById(R.id.contents);
        editText.setOnClickListener(this);
        buttonImg = (Button)findViewById(R.id.image);
        buttonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Image_Request_Code);
            }
        });

        upTest = (Button)findViewById(R.id.upTest);
        upTest.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.upTest:
                UploadImage();
                //main으로 돌아가기
                startActivity(new Intent(NewFeedActivity.this, MainActivity.class));
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    imageView.setImageBitmap(img);
                    FilePathUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String GetFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    UploadInfo up = new UploadInfo();

    public void UploadImage(){
        if(FilePathUri != null){
            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child( uid + "_feed_img/" + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //String TempImageName = contents.getText().toString().trim();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                    }
                });
            up.setImageURL(storageReference2);
        }
        else{
            Toast.makeText(NewFeedActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }
    }
}

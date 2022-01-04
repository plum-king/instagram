package com.example.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.Model.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

public class NewFeedActivity extends MainActivity implements View.OnClickListener {
    public static final String TAG = "NewFeedActivity";

    //카메라 기능 없는 코드
    ImageView imageView;
    Button buttonImg;
    Button upTest;
    EditText post_description, post_title;

    public FirebaseAuth mAuth;
    FirebaseUser mUser;
    public FirebaseDatabase database;
    public FirebaseStorage storage;
    //Firebase 'Post' entry
//    String uid, contentsTitle, contents;
    String uid;
/**/String description, postImg, title ,publisher, userImg;

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

        //uid로 userid 가져오기
        DatabaseReference mref;
/**/    mref = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("userid");
        Toast.makeText(NewFeedActivity.this, uid, Toast.LENGTH_SHORT);

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                publisher = snapshot.getValue(String.class);
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NewFeedActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

//        String num = null;
//        String filename = "feed" + num + ".jpg";

        progressDialog = new ProgressDialog(NewFeedActivity.this);
        imageView = (ImageView)findViewById(R.id.main_image);
///**/    content = (EditText) findViewById(R.id.post_description);
//        title = (EditText) findViewById(R.id.post_title);
        post_description = findViewById(R.id.post_description);
        post_title = findViewById(R.id.post_title);

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
//                contents = content.getText().toString();
//                contentsTitle = title.getText().toString();
/**/            //Post Model 이용하기
                description = post_description.getText().toString();
                title = post_title.getText().toString();
                UploadImage();
//                setFeed(description,postImg,publisher,userImg);
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
            progressDialog.setTitle("업로드 중");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child( uid + "_feed_img/" + post_title + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //String TempImageName = contents.getText().toString().trim();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "업로드 완료", Toast.LENGTH_LONG).show();
                        //ShortInfo에서 storyUrl 가져오기
                        DatabaseReference mref2;
                        mref2 = FirebaseDatabase.getInstance().getReference("ShortInfo").child(publisher).child("storyUrl");

                        mref2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                userImg = snapshot.getValue(String.class);
                                setFeed("", "", "https://lh3.googleusercontent.com/-8ThovF_SaJ0/W1xbDru2HZI/AAAAAAADmdM/DHY-uUP9EFkZIMO-ForkAyKuF2szAoBtACHMYCw/s0/adb04255fdd0b20303a1abbc8c463228d78568ce.jpg"
                                        , publisher, userImg);
                            }
                            @Override public void onCancelled(@NonNull DatabaseError error) {}
                        });
/**/                    storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DatabaseReference root = FirebaseDatabase.getInstance().getReference("Post");
                                postImg = uri.toString();
                                Post post = new Post(userImg, publisher, title, postImg, description);
                                root.child(publisher).setValue(post);
                                root.child(publisher).child("timestamp").setValue(ServerValue.TIMESTAMP);
                            }
                        });
                    }
                });
            up.setImageURL(storageReference2);

        }
        else{
            Toast.makeText(NewFeedActivity.this, "이미지를 선택해주세요", Toast.LENGTH_LONG).show();
        }
    }
/**/
    private void setFeed(String description, String title, String postImg, String publisher, String userImg) {
        DatabaseReference hopperRef = databaseReference.child("Post").child(publisher);
        Map<String, Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put("description", description);
        hopperUpdates.put("postImg", postImg);
        hopperUpdates.put("postTitle", title);
        hopperUpdates.put("publisher", publisher);
        hopperUpdates.put("userImg", userImg);
//        private String userImg;
//        private String publisher;
//        private String postImg;
//        private String postTitle;
//        private String description;

        hopperRef.updateChildren(hopperUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "저장을 완료했습니다", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "저장에 실패했습니다", Toast.LENGTH_LONG).show();
            }
        });
    }
}

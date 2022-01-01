//package com.example.login;
//
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.io.File;
//
//public class NewFeedActivity2 extends NewFeedActivity implements View.OnClickListener{
//
//    EditText editText;
//    String content; // 게시글
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_write);
//
//        Button button1=findViewById(R.id.back);
//        button1.setOnClickListener(this);
//        Button button2=findViewById(R.id.doneFeed);
//        button2.setOnClickListener(this);
//        editText = findViewById(R.id.content);
//        editText.setOnClickListener(this);
//    }
//
////    public void UploadText(){
////        content = editText.getText().toString();
////        Map<String, Object> uploadref = new HashMap<>();
////        uploadref.put("feed", content);
////        up.setText(content);
////        databaseReference.child("Users").child(uid).push()
////            .setValue(up.getText()).addOnSuccessListener(new OnSuccessListener<Void>() {
////            @Override
////            public void onSuccess(Void aVoid) {
////                Toast.makeText(getApplicationContext(),"저장을 완료했습니다", Toast.LENGTH_LONG).show(); //얘만 뜨긴 하는데 그냥 바로 함수 만들어서 그런듯
////            }
////            public void onFailure(Void aVoid){
////                Toast.makeText(getApplicationContext(),"저장을 실패했습니다", Toast.LENGTH_LONG).show();
////            }
////        });
////
////    }
//    //ibtn2.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            content = editText.getText().toString(); // 게시글
////            f = friends.getText().toString(); // 친구태그
////            l = location.getText().toString(); // 위치
//
//            final String uid = mAuth.getCurrentUser().getUid();
//            final Uri file = Uri.fromFile(new File(FilePathUri)); // 절대경로uri를 file에 할당
//            Log.d(TAG, "phto file : " + file);
//
//            StorageReference storageReference = storage.getReference().child("userImages").child("uid/"+file.getLastPathSegment());
//            storageReference.putFile(uriUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                    final Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
//                    while (!imageUrl.isComplete()) ;
//
//                    database.getReference().child("users").child(uid)
//                            .addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    SignUpUser signUpUser = dataSnapshot.getValue(SignUpUser.class);
//                                    //Log.d(TAG, "profileImageUrl" + signUpUser.profileImageUrl);
//                                    Log.d(TAG, "userId" + signUpUser.userid);
//
//                                    PostModel postModel = new PostModel();
//
//                                    postModel.myid = uid;
//                                    postModel.photo = imageUrl.getResult().toString();
//                                    //postModel.userprofileimage = userModel.profileImageUrl;
//                                    postModel.photoName = file.getLastPathSegment();
//                                    postModel.contents = content;
//                                    //postModel.location = l;
//                                    postModel.username = signUpUser.userid;
//
//                                    // 게시글 내용 저장
//                                    database.getReference().child("contents").child("content").push()
//                                            .setValue(postModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            //
//                                        }
//                                    });
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//
//                }
//            });
//        }
//
////    @Override
////    public void onClick(View view) {
////        switch (view.getId()){
////            case R.id.back:
////                finish();
////                break;
////            case R.id.doneFeed:
////                UploadImage(); //이미지랑 텍스트가 안올라감
////                UploadText();
////                break;
////        }
////    }
//}
//

package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    Button editProfileButton;
    TextView name, fullname, bio, web;
    CircleImageView profile_pic;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mDatabase;
    FirebaseStorage storage;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();     //현재 로그인한 유저
        uid = mUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();

        name = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        bio = findViewById(R.id.bio);
        web = findViewById(R.id.web);
        profile_pic = findViewById(R.id.profile_pic);

        readUser(uid);

        editProfileButton = findViewById(R.id.edit_profile);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void readUser(String uid) {
//        progressDialog.setMessage("Please wait while Registration...");
//        progressDialog.setTitle("Registration");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //progressDialog.dismiss();
                User user = snapshot.getValue(User.class);
                assert user != null;
                name.setText(user.userid);

                if(user.fullname != null) {
                    fullname.setText(user.fullname);
                }else{
                    fullname.setText("");
                }
                if(user.web != null) {
                    web.setText(user.web);
                }else{
                    web.setText("");
                }
                if(user.bio != null) {
                    bio.setText(user.bio);
                }else{
                    bio.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"데이터를 가져오는데 실패했습니다" , Toast.LENGTH_LONG).show();
            }
        });

        StorageReference storageRef = storage.getReference();
        StorageReference pathRef = storageRef.child("profile_pic");

        if(pathRef == null) {}
        else {
            StorageReference submitProfile = storageRef.child("profile_pic/" + uid + ".jpg");
            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getApplicationContext()).load(uri).into(profile_pic);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }
}
package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.Adapter.MyPostAdapter;
import com.example.Adapter.PostAdapter;
import com.example.Model.MyPost;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

import com.example.Model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    Button editProfileButton;
    TextView name, fullname, bio, web;
    TextView posts;
    CircleImageView profile_pic;
    ImageView new_content;

    RecyclerView mPostRecycler;
    MyPostAdapter myPostAdapter;
    List<MyPost> postList;
    String publisher;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mDatabase;
    FirebaseStorage storage;

    String uid;
    String profileid; // == name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();     //?????? ???????????? ??????
        uid = mUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();

        name = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        bio = findViewById(R.id.bio);
        web = findViewById(R.id.web);
        profile_pic = findViewById(R.id.profile_pic);

        posts = findViewById(R.id.posts);

        mPostRecycler = findViewById(R.id.recycler_view);
        mPostRecycler.setHasFixedSize(true);
                
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mPostRecycler.setLayoutManager(linearLayoutManager);

        postList = new ArrayList<>();
        getMyPostImgs();

        myPostAdapter = new MyPostAdapter(getApplicationContext(), postList);
        mPostRecycler.setAdapter(myPostAdapter);

        readUser(uid);
        getMyPostCnt();

        editProfileButton = findViewById(R.id.edit_profile);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        new_content = (ImageView) findViewById(R.id.new_content);
        new_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //????????? ??????
                Intent intent2 = new Intent(getApplicationContext(), NewFeedActivity.class);
                startActivity(intent2);
            }
        });
    }

    private void readUser(String uid) {
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
                Toast.makeText(getApplicationContext(),"???????????? ??????????????? ??????????????????" , Toast.LENGTH_LONG).show();
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

    private void getMyPostImgs() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Post");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MyPost post = dataSnapshot.getValue(MyPost.class);
                    //if(post.getPublisher().equals(uid)) {
                        postList.add(post);
                    //}
                }
                Collections.reverse(postList);
                myPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "error!" , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getMyPostCnt() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Post");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    //if(post.getPublisher().equals(profileid)) {
                        i++;
                    //}
                }
                posts.setText(String.valueOf(i));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}




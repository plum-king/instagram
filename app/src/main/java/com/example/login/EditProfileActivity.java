package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mDatabase;
    String uid;

    ImageView close, done, profile_pic;
    TextView edit_pic;
    EditText name, fullname, web, bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();     //현재 로그인한 유저

        mDatabase = FirebaseDatabase.getInstance().getReference();

        uid = mUser.getUid();

        Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_LONG).show();
        DatabaseReference email = mDatabase.child(uid).child("email");
        DatabaseReference userid = mDatabase.child(uid).child("userid");

        close = (ImageView) findViewById(R.id.close);
        done = (ImageView) findViewById(R.id.done);
        profile_pic = (ImageView) findViewById(R.id.profile_pic);
        edit_pic = (TextView) findViewById(R.id.edit_pic);
        name = (EditText) findViewById(R.id.edit_name);
        fullname = (EditText) findViewById(R.id.edit_fullname);
        web = (EditText) findViewById(R.id.edit_website);
        bio = (EditText) findViewById(R.id.edit_bio);


        readUser(uid);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        edit_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = name.getText().toString();
                String getFullname = fullname.getText().toString();
                String getWeb = web.getText().toString();
                String getBio = bio.getText().toString();

                if(getName.isEmpty()) {
                    name.setError("Name is Required");
                    name.requestFocus();
                    return;
                }

                setUser(uid, getName, getFullname, getWeb, getBio);
                finish();
            }
        });
    }

    private void readUser(String uid) {
        mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
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

                Toast.makeText(getApplicationContext(),"성공!" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"데이터를 가져오는데 실패했습니다" , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUser(String uid, String name, String fullname, String web, String bio) {
        DatabaseReference hopperRef = mDatabase.child("Users").child(uid);
        Map<String, Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put("userid", name);
        hopperUpdates.put("fullname", fullname);
        hopperUpdates.put("web", web);
        hopperUpdates.put("bio", bio);
        hopperRef.updateChildren(hopperUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"저장을 완료했습니다", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"저장에 실패했습니다" , Toast.LENGTH_LONG).show();
            }
        });


    }
}
package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.Model.Story;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE=0;
/**/private DatabaseReference root = FirebaseDatabase.getInstance().getReference("ShortInfo");

FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mDatabase;
    FirebaseStorage storage;

    String uid;

    ImageView close, done, profile_pic;
    TextView edit_pic;
    EditText name, fullname, web, bio;

    private Uri imageUri;

//    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();     //현재 로그인한 유저

        mDatabase = FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();

        uid = mUser.getUid();

        close = (ImageView) findViewById(R.id.close);
        done = (ImageView) findViewById(R.id.done);
        profile_pic = (ImageView) findViewById(R.id.profile_pic);
        edit_pic = (TextView) findViewById(R.id.edit_pic);
        name = (EditText) findViewById(R.id.edit_name);
        fullname = (EditText) findViewById(R.id.edit_fullname);
        web = (EditText) findViewById(R.id.edit_website);
        bio = (EditText) findViewById(R.id.edit_bio);
//        progressDialog = new ProgressDialog(this);

        readUser(uid);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        edit_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            imageUri = data.getData();
            try {
                InputStream in = getContentResolver().openInputStream(imageUri);
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                profile_pic.setImageBitmap(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                    Glide.with(EditProfileActivity.this).load(uri).into(profile_pic);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
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

        StorageReference storageRef = storage.getReference();
        StorageReference riversRef = storageRef.child("profile_pic/" + uid + ".jpg");
        UploadTask uploadTask = riversRef.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "사진이 정상적으로 업로드되지 않음", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
/**/            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Story story = new Story(name, uri.toString());
                        root.child(name).setValue(story);

                        Toast.makeText(EditProfileActivity.this, "Url Uploaded", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }
}
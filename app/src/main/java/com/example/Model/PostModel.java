package com.example.Model;

import java.util.HashMap;
import java.util.Map;

public class PostModel {
//    public String imageUrl;
    public String title;
    public String description;
//    public String uid;
    public String userId;

    public PostModel( String title, String description, String userId){
//        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
//        this.uid = uid;
        this.userId = userId;
    }

    public PostModel(){

    }

//    public String getImageUrl() {return imageUrl;}
    public String getTitle(){return title;}
    public String getDescription() {return description;}
//    public String getUid() {return uid;}
    public String getUserId() {return userId;}

//    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
//    public void setUid(String uid) {this.uid = uid;}
    public void setUserId(String userId) {this.userId = userId;}
}

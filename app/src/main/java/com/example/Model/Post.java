package com.example.Model;

public class Post {
    private String userImg;
    private String publisher;
    private String postImg;
    private String postTitle;
    private String description;

    //private String liker;
    //private String comments;
    //private String saver;
    public Post(String userImg, String publisher, String postTitle, String postImg, String description) {
        this.userImg = userImg;
        this.publisher = publisher;
        this.postTitle = postTitle;
        this.postImg = postImg;
        this.description = description;
        //this.liker = liker;
    }

    public Post() {}

    public String getUserImg() {return userImg;}

    public void setUserImg(String userImg) {this.userImg = userImg;}

    public String getPublisher() {return publisher;}

    public void setPublisher(String publisher) {this.publisher = publisher;}

    public String getPostImg() {return postImg;}

    public void setPostImg(String postImg) {this.postImg = postImg;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}


    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

//    public String getLiker() {return liker;}
//
//    public void setLiker(String liker) {this.liker = liker;}
}
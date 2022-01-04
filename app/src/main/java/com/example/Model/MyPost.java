package com.example.Model;

public class MyPost {
    private String postImg;
    private String publisher;


    public MyPost(String postImg, String publisher) {
        this.postImg = postImg;
        this.publisher = publisher;
    }

    public MyPost() {}

    public String getpostImg() {return postImg;}
    public String getPublisher() {return publisher;}
}

package com.example.login;

public class User {
    public String userid;
    public String fullname;
    public String web;
    public String bio;
    public String pic;

    public User(){}

    public User(String userid, String fullname, String web, String bio, String pic) {
        this.userid = userid;
        this.fullname=fullname;
        this.web=web;
        this.bio=bio;
        this.pic=pic;
    }
}

package com.example.login;

public class User {
    public String userid;
    public String fullname;
    public String web;
    public String bio;

    public User(){}

    public User(String userid, String fullname, String web, String bio) {
        this.userid = userid;
        this.fullname=fullname;
        this.web=web;
        this.bio=bio;
    }
}

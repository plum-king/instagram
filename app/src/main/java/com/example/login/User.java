package com.example.login;

public class User {
    public String userid;
    public String fullname;
    public String web;
    public String bio;
//
    public String url;

    public User(){}


    public User(String userid, String fullname, String web, String bio) {
        this.userid = userid;
        this.fullname=fullname;
        this.web=web;
        this.bio=bio;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    //
    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }
}

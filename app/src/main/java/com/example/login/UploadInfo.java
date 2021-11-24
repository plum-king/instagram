package com.example.login;

import com.google.firebase.storage.StorageReference;

public class UploadInfo {
    public
    StorageReference imageURL;
    public String text;

    public UploadInfo(){

    }

    public UploadInfo(StorageReference url, String content){
        this.imageURL = url;
        this.text = content;
    }

    public StorageReference getImageURL(){
        return imageURL;
    }
    public String getText() { return text; }
    public void setImageURL(StorageReference imageURL){
        this.imageURL = imageURL;
    }
    public void setText(String c){
        this.text = text;
    }

}

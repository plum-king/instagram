package com.example.Model;

public class Story {
    private String storyId;
    private String storyUrl;

    public Story(String storyId, String storyUrl) {
        this.storyId = storyId;
        this.storyUrl = storyUrl;
    }

    public Story() {
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getStoryUrl() {
        return storyUrl;
    }

    public void setStoryUrl(String storyUrl) {
        this.storyUrl = storyUrl;
    }
}

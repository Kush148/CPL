package com.example.cpl;

public class FeedbackList {

    int feedbackId;
    String email;
    String title;
    String description;

    public FeedbackList(int feedbackId, String email, String title, String description) {
        this.feedbackId = feedbackId;
        this.email = email;
        this.title = title;
        this.description = description;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}

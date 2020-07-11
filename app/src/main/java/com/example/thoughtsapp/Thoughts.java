package com.example.thoughtsapp;

import com.google.firebase.firestore.FieldValue;

import java.util.Date;

public class Thoughts {
    private String username ;
    private Date timestamp;
    private String ThoughtsText;
    private Integer numLikes;
    private Integer numComments;
    private String category;
    private String documentId;


    public Thoughts() {
    }

    public Thoughts(String username, Date timestamp, String thoughtsText, Integer numLikes, Integer numComments, String category, String documentId) {
        this.username = username;
        this.timestamp = timestamp;
        ThoughtsText = thoughtsText;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.category = category;
        this.documentId = documentId;
    }

    public Thoughts(String username, Date timestamp, String thoughtsText, Integer numLikes, Integer numComments, String documentId) {
        this.username = username;
        this.timestamp = timestamp;
        ThoughtsText = thoughtsText;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.documentId = documentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getThoughtsText() {
        return ThoughtsText;
    }

    public void setThoughtsText(String thoughtsText) {
        ThoughtsText = thoughtsText;
    }

    public Integer getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(Integer numLikes) {
        this.numLikes = numLikes;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}

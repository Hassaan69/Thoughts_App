package com.example.thoughtsapp.Model;

import java.util.Date;

public class Comments {
    String username;
    Date timestamp;
    String commentTxt;

    public Comments(String username, Date timestamp, String commentTxt) {
        this.username = username;
        this.timestamp = timestamp;
        this.commentTxt = commentTxt;
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

    public String getCommentTxt() {
        return commentTxt;
    }

    public void setCommentTxt(String commentTxt) {
        this.commentTxt = commentTxt;
    }
}

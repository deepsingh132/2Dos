package com.hackbaba.Doto;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class ToDos {

    // public String username;
    public String title;
    public String date;
    public String desc;
    public String key;


    public ToDos() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ToDos(String title,String date,String desc,String key) {
        // this.username = username;
        this.title = title;
        this.date = date;
        this.desc = desc;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
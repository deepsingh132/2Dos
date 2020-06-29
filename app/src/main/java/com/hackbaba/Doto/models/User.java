package com.hackbaba.Doto.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

   // public String username;
    public String email;
    public String name;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email,String name) {
       // this.username = username;
        this.email = email;
        this.name = name;
    }


    public void setDate(String date) {
        this.name = name;
    }

}
// [END blog_user_class]
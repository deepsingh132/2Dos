package com.hackbaba.Doto.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class UserwithPhone {

    // public String username;
    public String email;
    public String name;
    public String phone;

    public UserwithPhone() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserwithPhone(String email,String name,String phone) {
        // this.username = username;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

}
// [END blog_user_class]

package com.example.hackedin;

import io.realm.mongodb.User;

public class userDetail {
    private static User muser;
    public static void setUser(User user)
    {
        muser=user;
    }
    public static User getUser()
    {
        return muser;
    }
}

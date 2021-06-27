package com.example.hackedin;

import io.realm.mongodb.User;

public class userDetail {
    private static User muser;
    private static String emailId;
    private static String PassWord;
    public static void setUser(User user)
    {
        muser=user;
    }
    public static void setEmailId(String emailid)
    {
        emailId=emailid;
    }
    public static void setPassword(String password)
    {
        PassWord=password;
    }
    public static String getEmailId()
    {
        return emailId;
    }
    public static String getPassWord()
    {
        return PassWord;
    }
    public static User getUser()
    {
        return muser;
    }
}

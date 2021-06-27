package com.example.hackedin;

import android.provider.ContactsContract;

import androidx.work.Data;

public class DataObject {
    private String mTo;
    private String mSub;
    private String mBody;
    private String mCc;
    private int mEmailType;
    private int mdeleteType;
    public DataObject(String To,String Cc,String Sub,String Body,int emailType,int deleteType )
    {
        mTo = To;
        mCc=Cc;
        mSub = Sub;
        mBody=Body;
        mEmailType = emailType;
        mdeleteType=deleteType;
    }
    public Data dataReturn()
    {
        return (new Data.Builder().putString("To",mTo).putString("Cc",mCc)
                .putString("Subject",mSub).putString("Body",mBody).putInt("EmailType",mEmailType).putInt("isDelete",mdeleteType).build());
    }
}

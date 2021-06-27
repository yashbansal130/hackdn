package com.example.hackedin;

public class DocOb {
    private String mTo;
    private String mSub;
    private long mTime;
    private int mEmailType;
    private int mCount;

    public DocOb(String To,String Sub,long time,int count,int emailType )
    {
        mTo = To;
        mSub = Sub;
        mTime = time;
        mCount = count;
        mEmailType = emailType;
    }


    public String getTo() {
        return mTo;
    }
    public String getSub()
    {
        return mSub;
    }
    public long getTime()
    {
        return mTime;
    }
    public int getEmailType()
    {
        return mEmailType;
    }
    public int getCount()
    {
        return mCount;
    }
}

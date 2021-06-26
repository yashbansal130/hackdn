package com.example.hackedin;

public class nextTimeCalc {
    public static int emailType;
    public static long current;
    public static long next;
    public nextTimeCalc()
    {
        this.emailType=emailType;
        this.current=current;
        next=0;
    }
    public static long getNextTime()
    {
        switch(emailType)
        {
            case 0:
                next=current+30L;
                break;
            case 1:
                next=current+604800L;
                break;
            case 2:
                next=current+18144000L;
                break;
            case 3:
                next=current+18364752000L;
                break;
        }
        return next;
    }
}

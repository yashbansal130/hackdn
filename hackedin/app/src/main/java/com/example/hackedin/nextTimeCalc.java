package com.example.hackedin;

public  class nextTimeCalc {
    public  int emailType;
    public  long next;

    public nextTimeCalc(int emailType) {
        this.emailType=emailType;
        next=0;
    }

    public long getNextTime()
    {
        switch(emailType)
        {
            case 0:
                next=0;
                break;
            case 1:
                next=60*60;
                break;
            case 2:
                next=24*60*60;
                break;
            case 3:
                next=24*60*60*7;
                break;
        }
        return next;
    }
}

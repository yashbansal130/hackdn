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
                next=30000L;
                break;
            case 1:
                next=604800000L;
                break;
            case 2:
                next=18144000000L;
                break;
            case 3:
                next=18364752000000L;
                break;
        }
        return next;
    }
}

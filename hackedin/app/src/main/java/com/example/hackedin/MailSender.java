package com.example.hackedin;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class MailSender extends Worker {
    String toData;
    String ccData;
    String bodyData;
    String subData;
    int  deleteType;
    int emailType;
    Context context;
    WorkerParameters workerParameters;

    public MailSender(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        this.workerParameters = workerParams;
    }

    @NonNull
    @Override
    public Result doWork() {

        Data inputData=getInputData();
        Log.v("MailSender","curently in mailsender");
        toData=inputData.getString("To");
        ccData=inputData.getString("Cc");
        subData=inputData.getString("Subject");
        bodyData=inputData.getString("Body");
        emailType=inputData.getInt("EmailType",0);
        deleteType=inputData.getInt("isDelete",0);

        if(deleteType==0) {
            sendMail sendmail = new sendMail(toData, ccData, subData, bodyData);
            sendmail.toMail();
            Log.v("MailSender","Mailsent");
        }

        return Result.success();
    }
    @Override
    public void onStopped() {
        super.onStopped();
        Log.v("from Mailsender","Worker has been cancelled");
    }
}

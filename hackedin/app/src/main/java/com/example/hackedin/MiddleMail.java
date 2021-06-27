package com.example.hackedin;

import android.content.Context;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import org.bson.Document;

import java.util.concurrent.TimeUnit;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class MiddleMail {
    public Context mContext;
    User user;
    App app;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    private static final String appID = "application-0-aybxr";
    private static final String LOG_TAG = MiddleMail.class.getSimpleName();
    long timeStamp;
    String toData;
    String ccData;
    String bodyData;
    String subData;
    int emailType;
    int deleteType;
    WorkManager workManager;
    private WorkRequest workRequest;

    public MiddleMail(Context context, long timestamp) {

        this.mContext = context;
        timeStamp = timestamp;

    }

    protected void MailCall() {
        app = new App(new AppConfiguration.Builder(appID).build());
        user = userDetail.getUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("users");
        mongoCollection = mongoDatabase.getCollection("emails");
        Document queryFilter = new Document().append("userId", user.getId()).append("TimeOnSet", timeStamp);
        mongoCollection.findOne(queryFilter).getAsync(result ->
        {
            if (result.isSuccess()) {
                Log.v("Middle mail", "successfully found");
                Document prevRes = result.get();
                toData = prevRes.getString("To");
                ccData = prevRes.getString("CC");
                bodyData = prevRes.getString("Body");
                subData = prevRes.getString("Subject");
                emailType = prevRes.getInteger("EmailType");
                deleteType = prevRes.getInteger("isDelete");
                if (deleteType == 0) {
                    DataObject dataObject = new DataObject(toData, ccData, subData, bodyData, emailType, deleteType);
                    workManager = WorkManager.getInstance(mContext);
                    workRequest = new OneTimeWorkRequest.Builder(MailSender.class).setInputData(dataObject.dataReturn()).build();
                    workManager.enqueue(workRequest);

                }
            }
        });
    }
}



//                 if(emailType==0) {
//                        workRequest = new OneTimeWorkRequest.Builder(MailSender.class).setInputData(dataObject.dataReturn()).build();
//                        Log.v("Middle Mail", "work request created and applied");
//                        workManager.enqueue(workRequest);
//                    }
//                    if(emailType>0)
//                    {
//                        nextTimeCalc nexttime=new nextTimeCalc(emailType);
//                        long timeInter=nexttime.getNextTime();
//                        workRequest = new PeriodicWorkRequest.Builder(MailSender.class,timeInter , TimeUnit.MILLISECONDS)
//                                .setInputData(dataObject.dataReturn()).build();
//                        Log.v("Middle Mail", "work request created and applied");
//                        workManager.enqueue(workRequest);
//
//                    }

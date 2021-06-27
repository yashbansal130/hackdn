package com.example.hackedin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.view.Menu;


import org.bson.Document;

import java.util.Calendar;
import java.util.Date;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class Edit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    User user;
    App app;
    EditText toEdit;
    EditText ccEdit;
    EditText bodyEdit;
    EditText subEdit;
    String toData;
    String ccData;
    String bodyData;
    String subData;
    String[] timings = {"Recurring", "Weekly", "Monthly", "Yearly",};
    private  int emailType;
    private static final String appID = "application-0-aybxr";
    private static final String LOG_TAG = Edit.class.getSimpleName();
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    long currentTime;
    long prevTimeOnSet;
    int Counter;
    int prevemailType;
    Document prevRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Realm.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent prevIntent=getIntent();
        Counter=0;
        prevTimeOnSet=prevIntent.getLongExtra("timeOnSet",0L);

        app = new App(new AppConfiguration.Builder(appID).build());
        user = userDetail.getUser();
        toEdit = (EditText) findViewById(R.id.toContent);
        ccEdit = (EditText) findViewById(R.id.ccContent);
        bodyEdit = (EditText) findViewById(R.id.bodyContent);
        subEdit = (EditText) findViewById(R.id.subContent);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, timings);
        spin.setAdapter(adapter);

        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("users");
        mongoCollection = mongoDatabase.getCollection("emails");
        if(prevTimeOnSet!=0L)
        {
            Document queryFilter = new Document().append("userId", user.getId()).append("TimeOnSet", prevTimeOnSet);
            mongoCollection.findOne(queryFilter).getAsync(result->
            {
                if(result.isSuccess())
                {
                    prevRes=result.get();
                    toData=prevRes.getString("To");
                    ccData=prevRes.getString("CC");
                    bodyData=prevRes.getString("Body");
                    subData=prevRes.getString("Subject");
                    prevemailType=prevRes.getInteger("EmailType");
                    currentTime=prevRes.getLong("TimeOnSet");
                    Counter=prevRes.getInteger("sentCount");
                    toEdit.setText(toData);
                    ccEdit.setText(ccData);
                    bodyEdit.setText(bodyData);
                    subEdit.setText(subData);
                    spin.setSelection(emailType);
                    Log.v(LOG_TAG,"broadcasted the object found");
                }
                else
                {
                    prevRes=null;
                    Log.v(LOG_TAG,"object document found not broadcasted");
                }
            });
        }

    }

    public void onSave() {
        if(prevRes!=null)
        {
            onDocDeleted();
            prevRes=null;
        }
        toData = toEdit.getText().toString();
        ccData = ccEdit.getText().toString();
        bodyData = bodyEdit.getText().toString();
        subData = subEdit.getText().toString();
        currentTime = System.currentTimeMillis() ;
        if(prevRes==null) {
            mongoCollection.insertOne(new Document("userId", user.getId()).append("To", toData).append("CC", ccData).append("Body",
                    bodyData).append("Subject", subData).append("EmailType", emailType).append("TimeOnSet", currentTime).append("sentCount", 0).append("isDelete", 0)).getAsync(result -> {
                if (result.isSuccess()) {
                    Log.v(LOG_TAG, "Insertion is successful");
                    finish();
                } else {
                    Log.v(LOG_TAG, "INsertion was not successful" + result.getError().toString());
                }
            });
        }
        sendMail sendmail = new sendMail(toData, ccData, subData, bodyData);
        sendmail.toMail();

    }
    public void onDocDeleted()
    {
        if(prevTimeOnSet==0L)
        {
            return;
        }
        else
        {
            Document queryFilter = new Document().append("userId", user.getId()).append("TimeOnSet", prevTimeOnSet);
            Document temp=new Document("userId", user.getId()).append("To", toData).append("CC", ccData).append("Body",
                    bodyData).append("Subject", subData).append("EmailType", prevemailType).append("TimeOnSet", currentTime).append("sentCount", 0).append("isDelete", 1);
           mongoCollection.updateOne(queryFilter,temp).getAsync(result->
           {
               if(result.isSuccess())
               {

                   Log.v(LOG_TAG,"deleted");
                   finish();
               }
               else
               {
                   Log.v(LOG_TAG,"NOT deleted");
               }
           });

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        emailType = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(prevTimeOnSet==0){
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                onSave();
                return true;
            case R.id.action_delete:
                onDocDeleted();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
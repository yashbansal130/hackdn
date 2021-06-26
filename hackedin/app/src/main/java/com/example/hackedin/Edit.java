package com.example.hackedin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.DialogInterface;
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

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class Edit extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
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
    String[] timings = {"Recurring", "Weekly","Monthly", "Yearly",};
    private static int emailType;
    private static final String appID = "application-0-aybxr";
    private static final String LOG_TAG =Edit.class.getSimpleName();
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    long  currentTime;
    long nextTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Realm.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        app = new App(new AppConfiguration.Builder(appID).build());
        user = userDetail.getUser();
        toEdit = (EditText) findViewById(R.id.toContent);
        ccEdit = (EditText) findViewById(R.id.ccContent);
        bodyEdit = (EditText) findViewById(R.id.bodyContent);
        subEdit = (EditText) findViewById(R.id.subContent);
        Spinner spin =(Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, timings);
        spin.setAdapter(adapter);
        mongoClient=user.getMongoClient("mongodb-atlas");
        mongoDatabase=mongoClient.getDatabase("users");
       mongoCollection=mongoDatabase.getCollection("emails");


    }
    public void onSave()
    {
        toData = toEdit.getText().toString();
        ccData = ccEdit.getText().toString();
        bodyData = bodyEdit.getText().toString();
        subData = subEdit.getText().toString();
        currentTime = System.currentTimeMillis() / 1000L;
        nextTime=nextTimeCalc.getNextTime();

        mongoCollection.insertOne(new Document("userId",user.getId()).append("To",toData).append("CC",ccData).append("Body",
                bodyData).append("Subject",subData).append("EmailType",emailType).append("TimeOnSet",currentTime).
                append("TimeNext",nextTime).append("sentCount",0)).getAsync(result->{
                    if(result.isSuccess())
                    {
                        Log.v(LOG_TAG,"Insertion is successful");
                    }
                    else
                    {
                        Log.v(LOG_TAG,"INsertion was not successful"+result.getError().toString());
                    }
        });

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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                onSave();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
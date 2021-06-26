package com.example.hackedin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import org.bson.Document;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class home extends AppCompatActivity {
    User user;
    App app;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    private static final String appID = "application-0-aybxr";
    private static final String LOG_TAG =home.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        app = new App(new AppConfiguration.Builder(appID).build());
        user=userDetail.getUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("users");
        mongoCollection = mongoDatabase.getCollection("emails");
        Document queryFilter= new Document().append("userId",user.getId()).append("sentCount",0);
        RealmResultTask<MongoCursor<Document>> findTask =mongoCollection.find(queryFilter).iterator();
//        ArrayList<Document> homeArray=new ArrayList<Document>();
        findTask.getAsync(task->
        {
            if(task.isSuccess())
            {
                MongoCursor<Document> result=task.get();
                while(result.hasNext())
                {
                    Document curDoc=result.next();
//                    homeArray.add(curDoc);
                    Log.v(LOG_TAG,"sent to mail"+curDoc.get("To").toString());
                }
                Log.v(LOG_TAG,"successfully found the task");
            }
            else
            {
                Log.v(LOG_TAG,"task not found"+task.getError().toString());
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, Edit.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.history_item:
                break;
            case R.id.logout_item:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
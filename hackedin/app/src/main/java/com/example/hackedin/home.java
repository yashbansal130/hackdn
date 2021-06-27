package com.example.hackedin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.bson.Document;
import org.json.JSONObject;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
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
    ArrayList<DocOb> homeArray;
    ListView listView;
    customAdapter adapter;

    private static final String appID = "application-0-aybxr";
    private static final String LOG_TAG = home.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        app = new App(new AppConfiguration.Builder(appID).build());
        user = userDetail.getUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("users");
        mongoCollection = mongoDatabase.getCollection("emails");
        Document queryFilter = new Document().append("userId", user.getId()).append("isDelete", 0);
        homeArray = new ArrayList<DocOb>();
        RealmResultTask<MongoCursor<Document>> findTask = mongoCollection.find(queryFilter).iterator();


        findTask.getAsync(task ->
        {
            if (task.isSuccess()) {
                MongoCursor<Document> result = task.get();
                while (result.hasNext()) {
                    Document curDoc = result.next();
                    try {
                        DocOb docOb = new DocOb(curDoc.getString("To"), curDoc.getString("Subject"),curDoc.getLong("TimeOnSet"),
                                (int)curDoc.get("sentCount"),  (int)curDoc.get("EmailType"));
                        homeArray.add(docOb);
                    } catch (Exception e) {
                        Log.v(LOG_TAG, "fucking error", e);
                    }

                    Log.v(LOG_TAG, "sent to mail" + curDoc.get("To").toString());

                }
                updateUi();

                Log.v(LOG_TAG, "successfully found the task");
            } else {
                Log.v(LOG_TAG, "task not found" + task.getError().toString());
            }

        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, Edit.class);
                startActivity(intent);
                Log.v(LOG_TAG,"after sending intent to edit view");
            }
        });
    }

    public void updateUi() {

        if (homeArray == null) {
            Log.v("LOG_TAG", "size of home array" + homeArray.size());
            return;
        }
        try {
//            Log.v("LOG_TAG", "yaar kyuu khaali hai tu" + home.this);
            listView = (ListView) findViewById(R.id.listContainer);
             adapter = new customAdapter(this,R.layout.list_item, homeArray, 0);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    long timeSet=homeArray.get(position).getTime();
                    Intent intent=new Intent(home.this,Edit.class);
                    intent.putExtra("timeOnSet",timeSet);
                    startActivity(intent);
                }
            });
        } catch (NullPointerException e) {
            Log.v("LOG_TAG", "yaar wapis se", e);
        }
    }
    public void toHistory()
    {
        Intent intent=new Intent(home.this,History.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history_item:
                toHistory();
                break;
            case R.id.logout_item:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
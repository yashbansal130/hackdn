package com.example.hackedin;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;

import java.util.ArrayList;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class History extends AppCompatActivity {
    User user;
    App app;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    ArrayList<DocOb> historyArray;
    private static final String appID = "application-0-aybxr";
    private static final String LOG_TAG = History.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        app = new App(new AppConfiguration.Builder(appID).build());
        user = userDetail.getUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("users");
        mongoCollection = mongoDatabase.getCollection("emails");
        Document queryFilter = new Document().append("userId", user.getId()).append("isDelete",1);
        historyArray = new ArrayList<DocOb>();
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
                        historyArray.add(docOb);
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

    }
    public void updateUi() {

        if (historyArray == null) {
            Toast.makeText(getApplicationContext(),"NO MAIL ENTRIES TO SHOW!",Toast.LENGTH_LONG).show();
            Log.v("LOG_TAG", "Error in history array" + historyArray.size());
            return;
        }
        try {
            Log.v("LOG_TAG", "History error" + History.this);
            ListView listView = (ListView) findViewById(R.id.historyList);
            customAdapter adapter = new customAdapter(this,R.layout.list_item, historyArray, 1);
            listView.setAdapter(adapter);
        } catch (NullPointerException e) {
            Log.v("LOG_TAG", "Null pointer exception", e);
        }
    }
}

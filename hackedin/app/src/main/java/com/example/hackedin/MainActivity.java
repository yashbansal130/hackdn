package com.example.hackedin;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        App app = new App(new AppConfiguration.Builder("application-0-aybxr").build());
        Credentials emailPasswordCredentials = Credentials.emailPassword("byash764438@gmail.com", "Yash@2019");
        AtomicReference<User> user = new AtomicReference<User>();
        app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                Log.v("AUTH", "Successfully authenticated using an email and password.");
                user.set(app.currentUser());
            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });
    }
}
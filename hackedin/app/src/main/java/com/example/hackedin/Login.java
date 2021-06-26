package com.example.hackedin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class Login extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPassword;
    Button buttonloginSubmit;
    App app;
    private static final String appID = "hachedin-giiqj";
    private static final String LOG_TAG =Login.class.getSimpleName();
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonloginSubmit = (Button) findViewById(R.id.login_button);
        inputEmail = findViewById(R.id.input_login_email);
        inputPassword = findViewById(R.id.input_login_password);
        app = new App(new AppConfiguration.Builder("application-0-aybxr").build());

        buttonloginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);
                app.loginAsync(emailPasswordCredentials, it -> {
                    if (it.isSuccess()) {
                        Log.v("AUTH", "Successfully authenticated using an email and password.");
                        user=app.currentUser();
                        Log.v(LOG_TAG,"user id="+user);
                    } else {
                        Log.e(LOG_TAG, it.getError().toString());
                    }
                });
            }
        });
    }
}
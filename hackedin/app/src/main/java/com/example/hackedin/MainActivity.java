package com.example.hackedin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class MainActivity extends AppCompatActivity {

    EditText signupEmail;
    EditText signupPassword;
    Button registerButton;
    TextView loginActivity;
    Button buttonloginSubmit;
    App app;
    private static final String appID = "hachedin-giiqj";
    private static final String LOG_TAG =MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signupEmail = findViewById(R.id.input_signup_email);
        signupPassword = findViewById(R.id.input_signup_password);
        registerButton = findViewById(R.id.register_button);
        loginActivity = findViewById(R.id.login_activity);
        String email = signupEmail.getText().toString();
        String pwd = signupPassword.getText().toString();
        Realm.init(this);
        app = new App(new AppConfiguration.Builder(appID).build());
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.getEmailPassword().registerUserAsync(email, pwd, it ->
                {
                    if (it.isSuccess()) {
                        Log.v(LOG_TAG, "signup successful");
                        Intent loginIntent = new Intent(MainActivity.this, Login.class);
                        startActivity(loginIntent);
                    } else {
                        Log.v(LOG_TAG, "signup unsucessful"+it.getError());
                    }
                });
            }
        });
        loginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, Login.class);
                startActivity(loginIntent);
            }
        });


    }

}
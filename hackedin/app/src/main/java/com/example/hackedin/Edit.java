package com.example.hackedin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
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
    String[] timings = {"Recurring", "Weekly","Monthly", "Yearly",};
    private static int emailType;
    private static final String appID = "application-0-aybxr";
    private static final String LOG_TAG =Edit.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
//        Realm.init(this);
//        app = new App(new AppConfiguration.Builder(appID).build());
//        //user = userDetail.getUser();
        toEdit = (EditText) findViewById(R.id.toContent);
        ccEdit = (EditText) findViewById(R.id.ccContent);
        bodyEdit = (EditText) findViewById(R.id.bodyContent);
        subEdit = (EditText) findViewById(R.id.subContent);
        Spinner spin =(Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, timings);
        spin.setAdapter(adapter);
//        Button saveButton = (Button) findViewById(R.id.save);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toData = toEdit.getText().toString();
//                ccData = ccEdit.getText().toString();
//                bodyData = bodyEdit.getText().toString();
//                subData = subEdit.getText().toString();
//            }
//        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
package com.example.hackedin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import io.realm.mongodb.User;

public class Edit extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    User user;
    EditText toEdit;
    EditText ccEdit;
    EditText bodyEdit;
    EditText subEdit;
    String[] timings = { "Recurring", "Weekly",
            "Monthly", "Yearly",};
    private static int emailType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        user=userDetail.getUser();
        toEdit=(EditText)findViewById(R.id.toContent);
        ccEdit=(EditText)findViewById(R.id.ccContent);
        bodyEdit=(EditText)findViewById(R.id.bodyContent);
        subEdit=(EditText)findViewById(R.id.subContent);
        Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter adapter= new ArrayAdapter( this,  android.R.layout.simple_spinner_item,timings);
        spin.setAdapter(adapter);
        String toData=toEdit.getText().toString();
        String ccData=ccEdit.getText().toString();
        
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        emailType=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
package com.example.hackedin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class customAdapter extends ArrayAdapter<DocOb> {
    private int mlistType;
    private int mres;
    public customAdapter( Context context,int res, ArrayList<DocOb> arrayDoc, int listType) {
        super(context, res,arrayDoc);
        mlistType=listType;
        mres=res;
    }
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DocOb curDoc=getItem(position);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(mres,parent,false);
        }
        TextView toText=(TextView)convertView.findViewById(R.id.listTo);
        TextView subText=(TextView)convertView.findViewById(R.id.listSub);
        TextView emailTypeText=(TextView)convertView.findViewById(R.id.listEmailType);
        TextView dateText=(TextView)convertView.findViewById(R.id.listDate);
        TextView timeText=(TextView)convertView.findViewById(R.id.listTime);
        String todata=curDoc.getTo();
        toText.setText(todata);
        String subdata=curDoc.getSub();
        subText.setText(subdata);
        int emailTypeNum=curDoc.getEmailType();
        String emailTypeName="";
        switch(emailTypeNum)
        {
            case 0:
               emailTypeName+="Instantly";
               break;
            case 1:
                emailTypeName+="An Hour Later";
                break;
            case 2:
                emailTypeName+="A Day Later";
                break;
            case 3:
                emailTypeName+="A Week Later";
                break;
        }
        emailTypeText.setText(emailTypeName);
        long timeOn=curDoc.getTime();
        Date itemDate = new Date(timeOn);
        String formattedTime = formatTime(itemDate);
        String formattedDate = formatDate(itemDate);

        dateText.setText(formattedDate);
        timeText.setText(formattedTime);
        TextView countText=(TextView)convertView.findViewById(R.id.listCount);
        int c=curDoc.getCount();


        countText.setVisibility(View.GONE);

        return convertView;
    }
}

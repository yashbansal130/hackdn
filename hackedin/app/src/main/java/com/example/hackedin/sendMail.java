package com.example.hackedin;

import android.app.ProgressDialog;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Properties;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sendMail {
    String mTo;
    String mCC;
    String mSubject;
    String mBody;
    String mEmail;
    String mPassword;
    Context context;
    public sendMail(String To, String CC, String Subject, String Body,Context mcontext) {
        mTo = To;
        mCC = CC;
        mBody = Body;
        mSubject = Subject;
        context=mcontext;
    }


    public void toMail() {
        mEmail=userDetail.getEmailId();
        mPassword=userDetail.getPassWord();
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mEmail, mPassword);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mEmail));
            InternetAddress uri = new InternetAddress(mTo.trim());

            message.setRecipient(Message.RecipientType.TO, uri);
            if(mCC!=null || mCC.length()>5) {
                InternetAddress uri2 = new InternetAddress(mCC.trim());
                message.setRecipient(Message.RecipientType.CC, uri2);
            }
            message.setSubject(mSubject);
            message.setText(mBody);
            new SendMail().onBackground(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    private class  SendMail {
        protected void onBackground(Message... messages){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(messages[0]);
                        Toast.makeText(context,"MAIL SENT SUCCESSFULLY!!",Toast.LENGTH_LONG).show();
                        Log.v("task", "Sucesss");
                    } catch (MessagingException e) {
                        Toast.makeText(context,"MAIL NOT SENT!! "+e,Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        Log.v("task", "failure");
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
}
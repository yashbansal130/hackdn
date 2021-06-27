package com.example.hackedin;

import android.app.ProgressDialog;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import android.util.Log;
import java.util.Properties;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sendMail {
    String mTo;
    String mCC;
    String mSubject;
    //int mSchedule;
    String mBody;
    String mEmail;
    String mPassword;
    public sendMail(String To, String CC, String Subject, String Body) {
        mTo = To;
        mCC = CC;
        mBody = Body;
        mSubject = Subject;
    }

    public sendMail(String To, String CC, String Subject, String Body) {
        mTo = To;
        mCC = CC;
        mBody = Body;
        //mSchedule = Schedule;
        mSubject = Subject;
        mEmail = "byash764438@gmail.com";
        mPassword = "_yash__bansal_";
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
            message.setRecipient(Message.RecipientType.CC, uri);
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
                        Log.v("task", "Sucesss");
                    } catch (MessagingException e) {
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
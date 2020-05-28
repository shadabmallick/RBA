package com.film.rba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.film.rba.R;
import com.film.rba.connection.InternetConnection;
import com.film.rba.util.GlobalClass;
import com.film.rba.util.Shared_Preference;

public class Splash extends AppCompatActivity {
    Handler handler;
    GlobalClass globalClass;
    Shared_Preference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(this);
        preference.loadPrefrence();
        run();
    }


    public void run() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                if (InternetConnection.checkConnection(Splash.this)) {

                    if(globalClass.getLogin_status().equals(true)){
                        Intent intent = new Intent(Splash.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(Splash.this, LoginActivity.class);
                        //Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    // Internet Available...
                   // Toast.makeText(Splash.this, "Internet Enabled!", Toast.LENGTH_SHORT).show();
                    //  Intent intent = new Intent(SplashScreenActivity.this, AppInfoActivity.class);


                } else {
                    // Internet Not Available...
                    Toast.makeText(Splash.this, "Please turn on your internet!", Toast.LENGTH_SHORT).show();



                    /*  *//** Creating a pending intent which will be broadcasted when an sms message is successfully sent *//*
                    PendingIntent piSent = PendingIntent.getBroadcast(getBaseContext(), 0, new Intent("in.wptrafficanalyzer.sent"), 0);

                    *//** Creating a pending intent which will be broadcasted when an sms message is successfully delivered *//*
                    PendingIntent piDelivered = PendingIntent.getBroadcast(getBaseContext(), 0, new Intent("in.wptrafficanalyzer.delivered"), 0);

                    *//** Getting an instance of SmsManager to sent sms message from the application*//*
                    SmsManager smsManager = SmsManager.getDefault();

                    *//** Sending the Sms message to the intended party *//*
                    smsManager.sendTextMessage("+918951776150", null, "Someone Theft your Friend Phone", piSent, piDelivered);*/
                }
            }
        }, 3000);
    }



}

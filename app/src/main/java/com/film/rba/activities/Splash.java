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
        setContentView(R.layout.activity_splash);

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
                    } else {
                        Intent intent = new Intent(Splash.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText(Splash.this, "Please turn on your internet!", Toast.LENGTH_SHORT).show();
                }
            }
        }, 3000);
    }



}

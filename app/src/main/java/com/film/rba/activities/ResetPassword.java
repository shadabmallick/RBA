package com.film.rba.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.film.rba.R;

public class ResetPassword extends AppCompatActivity {
    ImageView iv_eye;
    EditText edtUsername,edtPassword;
    TextView txt_register;
    boolean password_visible = false;
    RelativeLayout rl_login;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_activity);
        rl_login=findViewById(R.id.rl_login);

        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
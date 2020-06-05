package com.film.rba.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.film.rba.R;
import com.film.rba.adapter.SearchAdapter;
import com.film.rba.model.Event;
import com.film.rba.util.GlobalClass;
import com.film.rba.util.Shared_Preference;

import java.util.ArrayList;
import java.util.List;

public class SettingActvity extends AppCompatActivity {
    TextView txt_plan, tv_name, tv_phone;
    ImageView iv_change_password, img_back;

    GlobalClass globalClass;
    Shared_Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(this);
        preference.loadPrefrence();
        txt_plan=findViewById(R.id.txt_plan);
        tv_name=findViewById(R.id.tv_name);
        tv_phone=findViewById(R.id.tv_phone);
        iv_change_password=findViewById(R.id.iv_change_password);
        img_back=findViewById(R.id.img_back);

        tv_name.setText(globalClass.getName());
        tv_phone.setText(globalClass.getPhone_number());

        iv_change_password.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
            startActivity(intent);
        });

        txt_plan.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),PlanActvity.class);
            startActivity(intent);
        });
        img_back.setOnClickListener(v -> {
            finish();
        });



    }


}

package com.film.rba.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    TextView txt_plan;

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

        txt_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),PlanActvity.class);
                startActivity(intent);
            }
        });


    }


}

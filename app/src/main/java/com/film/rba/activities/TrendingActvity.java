package com.film.rba.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.film.rba.R;
import com.film.rba.adapter.SearchAdapter;
import com.film.rba.model.Event;

import java.util.ArrayList;
import java.util.List;

public class TrendingActvity extends AppCompatActivity {
    private List<Event> groceryList1 = new ArrayList<>();
    private RecyclerView groceryRecyclerView;
    private SearchAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trending_fragment);
        groceryRecyclerView=findViewById(R.id.recycler2);
       /* populategroceryList1();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3, LinearLayoutManager.VERTICAL,false);
        groceryRecyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        eventAdapter = new SearchAdapter(groceryList1, getApplicationContext());
        groceryRecyclerView.setAdapter(eventAdapter);*/
    }


}

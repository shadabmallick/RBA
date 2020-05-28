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

public class TrendingActvity   extends AppCompatActivity {
    private List<Event> groceryList1 = new ArrayList<>();
    private RecyclerView groceryRecyclerView;
    private SearchAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trending_fragment);
        groceryRecyclerView=findViewById(R.id.recycler2);
        populategroceryList1();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3, LinearLayoutManager.VERTICAL,false);
        groceryRecyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        eventAdapter = new SearchAdapter(groceryList1, getApplicationContext());
        groceryRecyclerView.setAdapter(eventAdapter);
    }

    private void populategroceryList1(){
        groceryList1.clear();
        Event event1 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.mov_img1,"Duration: 6hrs","Fees: Free");

        Event event2 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.mov_img2,"Duration: 6hrs","Fees: Free");
        Event event3 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.mov_img3,"Duration: 6hrs","Fees: Free");
        Event event4 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.mov_img4,"Duration: 6hrs","Fees: Free");
        Event event5= new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.mov_img5,"Duration: 6hrs","Fees: Free");
        Event event6 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.mov_img6,"Duration: 6hrs","Fees: Free");
        Event event7 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.mov_img7,"Duration: 6hrs","Fees: Free");
        Event event8 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.mov_img8,"Duration: 6hrs","Fees: Free");



        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event4);
        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryList1.add(event4);
        groceryList1.add(event5);
        groceryList1.add(event6);
        groceryList1.add(event7);
        groceryList1.add(event8);
        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryList1.add(event4);
        groceryList1.add(event5);
        groceryList1.add(event6);
        groceryList1.add(event7);
        groceryList1.add(event8);

        // eventAdapter.notifyDataSetChanged();
    }

}

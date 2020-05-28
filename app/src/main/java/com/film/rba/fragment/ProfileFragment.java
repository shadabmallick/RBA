package com.film.rba.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.film.rba.R;
import com.film.rba.activities.HomeScreen;
import com.film.rba.activities.LoginActivity;
import com.film.rba.adapter.ContinueWatchingAdapter;
import com.film.rba.adapter.EventAdapter;
import com.film.rba.adapter.MostViewed;
import com.film.rba.adapter.RecommendedAdapter;
import com.film.rba.adapter.WatchListAdapter;
import com.film.rba.model.Event;
import com.film.rba.networkService.AppConfig;
import com.film.rba.util.GlobalClass;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.film.rba.networkService.AppConfig.login;

public class ProfileFragment extends androidx.fragment.app.Fragment {
    View view;
    TextView tool_title;
    ImageView img_profile,back;
    LinearLayout ll_profile,ll_setting;
    RelativeLayout rel_tool;

   // private List<notice> groceryList = new ArrayList<>();
    private List<Event> groceryList1 = new ArrayList<>();
    private RecyclerView groceryRecyclerView,recyclerView,recyclerView2;
    private WatchListAdapter groceryAdapter;
    private ContinueWatchingAdapter eventAdapter;
    private RecommendedAdapter recommendedAdapter;
    Toolbar toolbar;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;
    GridView androidGridView;
    TabLayout tabs;
  ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile, container, false);




        recyclerView=view.findViewById(R.id.recycler1);
        groceryRecyclerView=view.findViewById(R.id.recycler2);
        recyclerView2=view.findViewById(R.id.recycler3);

        populategroceryList1();


        eventAdapter = new ContinueWatchingAdapter(groceryList1, getActivity());
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager1);
        recyclerView.setAdapter(eventAdapter);


        groceryAdapter = new WatchListAdapter(groceryList1, getActivity());
        LinearLayoutManager horizontalLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        groceryRecyclerView.setLayoutManager(horizontalLayoutManager2);
        groceryRecyclerView.setAdapter(groceryAdapter);


        recommendedAdapter = new RecommendedAdapter(groceryList1, getActivity());
        LinearLayoutManager horizontalLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(horizontalLayoutManager3);
        recyclerView2.setAdapter(recommendedAdapter);

        return view;
    }
/*
    private void populategroceryList(){
        groceryList.clear();
        notice notice1 = new notice("Morning Assembly Cancelled ","Junior High school assembly has been cancelled today due to flu",
                "40 min ago");
        notice notice2 = new notice("Morning Assembly Cancelled ","Junior High school assembly has been cancelled today due to flu",
                "40 min ago");
        notice notice3 = new notice("Morning Assembly Cancelled ","Junior High school assembly has been cancelled today due to flu",
                "40 min ago");
        notice notice4 = new notice("Morning Assembly Cancelled ","Junior High school assembly has been cancelled today due to flu",
                "40 min ago");
        notice notice5 = new notice("Morning Assembly Cancelled ","Junior High school assembly has been cancelled today due to flu",
                "40 min ago");
        notice notice6 = new notice("Morning Assembly Cancelled ","Junior High school assembly has been cancelled today due to flu",
                "40 min ago");

        groceryList.add(notice1);
        groceryList.add(notice2);
        groceryList.add(notice3);
        groceryList.add(notice4);
        groceryList.add(notice5);
        groceryList.add(notice6);
       // groceryAdapter.notifyDataSetChanged();
    }
*/
    private void populategroceryList1(){
        groceryList1.clear();
        Event event1 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.mov_img1,"Duration: 6hrs","Fees: Free");

        Event event2 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.mov_img2,"Duration: 6hrs","Fees: Free");
        Event event3 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.mov_img3,"Duration: 6hrs","Fees: Free");



        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
       // eventAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();

    }



}
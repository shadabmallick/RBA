package com.film.rba.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.film.rba.R;
import com.film.rba.adapter.MostViewedAdapter;
import com.film.rba.adapter.SearchAdapter;
import com.film.rba.model.Event;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment  {
    View view;
    TextView tool_title;
    ImageView img_profile,back;
    LinearLayout ll_profile,ll_setting;
    RelativeLayout rel_tool;

   // private List<notice> groceryList = new ArrayList<>();
    private List<Event> groceryList1 = new ArrayList<>();
    private RecyclerView groceryRecyclerView,recyclerView,recyclerView2;
    private MostViewedAdapter groceryAdapter;
    private SearchAdapter eventAdapter;
    Toolbar toolbar;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;
    GridView androidGridView;
    TabLayout tabs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);

      //  rel_tool=getActivity().findViewById(R.id.rel_tool);
     //   rel_tool.setVisibility(View.GONE);
        groceryRecyclerView=view.findViewById(R.id.recycler2);


        populategroceryList1();



        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3,LinearLayoutManager.VERTICAL,false);
        //groceryRecyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //eventAdapter = new SearchAdapter(groceryList1, getActivity());
        //groceryRecyclerView.setAdapter(eventAdapter);


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


    @Override
    public void onResume() {
        super.onResume();

    }

}
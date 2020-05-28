package com.film.rba.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.film.rba.R;
import com.film.rba.activities.JustArrivedActvity;
import com.film.rba.activities.TrendingActvity;
import com.film.rba.adapter.BannerAdapter;
import com.film.rba.adapter.EventAdapter;
import com.film.rba.adapter.MostViewed;
import com.film.rba.adapter.TopScrollAdapter;
import com.film.rba.adapter.TrendingAdapter;
import com.film.rba.model.Event;

import com.film.rba.networkService.AppConfig;
import com.film.rba.util.GlobalClass;
import com.film.rba.util.Shared_Preference;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;
import static com.film.rba.networkService.AppConfig.home;
import static java.lang.System.exit;

public class HomeFragment extends Fragment  {
    View view;
    TextView tool_title,show_more_recent,show_more_watch;
    ImageView img_profile,back;
    LinearLayout ll_profile,ll_setting;
    RelativeLayout rel_tool;
    GlobalClass globalClass;
   Shared_Preference preference;
    private List<Event> groceryList1 = new ArrayList<>();
    private RecyclerView groceryRecyclerView,recyclerView,recyclerView2,recyclertop;
    private MostViewed groceryAdapter;
    private EventAdapter eventAdapter;
    private TrendingAdapter trendingActvity;
    private BannerAdapter topScrollAdapter;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> banner_list;
    ArrayList<HashMap<String,String>> wish_list;
    ArrayList<HashMap<String,String>> trending_list;
    ArrayList<HashMap<String,String>> history_list;
    ArrayList<HashMap<String,String>> suggestion_list;
    ArrayList<HashMap<String,String>> most_viewed_list;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;
    GridView androidGridView;
    TabLayout tabs;
    ViewPager viewPager;
    private static int NUM_PAGES = 5;
    private Runnable Update;
    int currentPage = 0;
    private Timer swipeTimer;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

      //  rel_tool=getActivity().findViewById(R.id.rel_tool);
     //   rel_tool.setVisibility(View.GONE);
        globalClass = (GlobalClass)getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
         recyclerView=view.findViewById(R.id.recycler1);
        groceryRecyclerView=view.findViewById(R.id.recycler2);
        recyclerView2=view.findViewById(R.id.recycler3);
        viewPager = view.findViewById(R.id.viewPager);

        show_more_recent=view.findViewById(R.id.show_more_recent);
        show_more_watch=view.findViewById(R.id.txt_label3);
        banner_list = new ArrayList<>();
        wish_list = new ArrayList<>();
        trending_list = new ArrayList<>();
        history_list = new ArrayList<>();
        suggestion_list = new ArrayList<>();
        most_viewed_list = new ArrayList<>();
        populategroceryList1();
        BannerList();


        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager1);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                // addDot(i);

            }
            @Override
            public void onPageSelected(int i) {
                //  addDot(i);
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });



        groceryAdapter = new MostViewed(groceryList1, getActivity());
        LinearLayoutManager horizontalLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        groceryRecyclerView.setLayoutManager(horizontalLayoutManager2);
        groceryRecyclerView.setAdapter(groceryAdapter);


        trendingActvity = new TrendingAdapter(groceryList1, getActivity());
        LinearLayoutManager horizontalLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(horizontalLayoutManager3);
        recyclerView2.setAdapter(trendingActvity);
        show_more_recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show_more=new Intent(getActivity(), JustArrivedActvity.class);
                        startActivity(show_more);
            }
        });
        show_more_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show_more=new Intent(getActivity(), JustArrivedActvity.class);
                        startActivity(show_more);
            }
        });

        return view;
    }
    private void initViewPager(ArrayList<HashMap<String,String>> banner_list){
        topScrollAdapter = new BannerAdapter(getActivity(),banner_list);
        viewPager.setAdapter(topScrollAdapter);




        NUM_PAGES = banner_list.size();

        Update = () -> {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, true);
        };

        handler.removeCallbacks(Update);


        if (swipeTimer == null){
            swipeTimer = new Timer();

            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 4000, 4000);

            handler.postDelayed(Update,4000);
        }

    }

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
    private void BannerList() {        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_DEV+home, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());



                Gson gson = new Gson();

                try
                {
                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Boolean status = jobj.get("success").getAsBoolean();
                    if(status==true) {
                        JsonObject data=jobj.getAsJsonObject("data");
                        JsonObject banner=data.getAsJsonObject("banner");
                        String name = banner.get("name").getAsString().replaceAll("\"", "");
                        String key = banner.get("key").getAsString().replaceAll("\"", "");

                        JsonArray categories=banner.getAsJsonArray("list");
                        if(categories != null ){
                            for (int j = 0; j < categories.size(); j++) {
                                JsonObject images1 = categories.get(j).getAsJsonObject();
                                String admin_video_id = images1.get("admin_video_id").toString().replaceAll("\"", "");
                                String title = images1.get("title").toString().replaceAll("\"", "");
                                String default_image = images1.get("default_image").toString().replaceAll("\"", "");
                                int ratings = Integer.parseInt(images1.get("ratings").toString().replaceAll("\"", ""));
                                HashMap<String, String> Category = new HashMap<>();
                                // Category.put("product_sub_id",product_sub_id);
                                Category.put("admin_video_id",admin_video_id);
                                Category.put("title",title);
                                Category.put("default_image",AppConfig.image_upload+default_image);
                                Category.put("ratings", String.valueOf(ratings));
                                banner_list.add(Category);
                                initViewPager(banner_list);


                            }

                            ///Adapter

                        }

                        JsonObject wishlist=data.getAsJsonObject("wishlist");
                        String wname = wishlist.get("name").getAsString().replaceAll("\"", "");
                        String wkey = wishlist.get("key").getAsString().replaceAll("\"", "");
                        JsonObject list=wishlist.getAsJsonObject("list");
                        int totalvideo = Integer.parseInt(list.get("totalvideo").toString().replaceAll("\"", ""));
                        JsonArray videos=list.getAsJsonArray("videos");
                        if(videos != null ){
                            for (int j = 0; j < videos.size(); j++) {
                                JsonObject images1 = videos.get(j).getAsJsonObject();
                                String admin_video_id = images1.get("admin_video_id").toString().replaceAll("\"", "");
                                String title = images1.get("title").toString().replaceAll("\"", "");
                                String description = images1.get("description").toString().replaceAll("\"", "");
                                String duration = images1.get("duration").toString().replaceAll("\"", "");
                                String third_image = images1.get("third_image").toString().replaceAll("\"", "");
                                String category_name = images1.get("category_name").toString().replaceAll("\"", "");
                                String default_image = images1.get("default_image").toString().replaceAll("\"", "");
                                String publish_time = images1.get("publish_time").toString().replaceAll("\"", "");
                                int category_id = Integer.parseInt(images1.get("category_id").toString().replaceAll("\"", ""));
                                int watch_count = Integer.parseInt(images1.get("watch_count").toString().replaceAll("\"", ""));
                                int ratings = Integer.parseInt(images1.get("ratings").toString().replaceAll("\"", ""));
                                HashMap<String, String> Category = new HashMap<>();
                                // Category.put("product_sub_id",product_sub_id);
                                Category.put("admin_video_id",admin_video_id);
                                Category.put("title",title);
                                Category.put("description",description);
                                Category.put("duration",duration);
                                Category.put("third_image",third_image);
                                Category.put("category_name",category_name);
                                Category.put("default_image",AppConfig.image_upload+default_image);
                                Category.put("publish_time",publish_time);
                                Category.put("category_id", String.valueOf(category_id));
                                Category.put("watch_count", String.valueOf(watch_count));
                                Category.put("ratings", String.valueOf(ratings));
                                 wish_list.add(Category);


                            }
/////////////////////////////////////Adapter
                            eventAdapter = new EventAdapter(wish_list, getActivity());
                            recyclerView.setAdapter(eventAdapter);
                        }
                        JsonObject trending=data.getAsJsonObject("trending");
                        String tname = trending.get("name").getAsString().replaceAll("\"", "");
                        String tkey = trending.get("key").getAsString().replaceAll("\"", "");
                        JsonObject tlist=trending.getAsJsonObject("list");
                        int t_totalvideo = Integer.parseInt(tlist.get("totalvideo").toString().replaceAll("\"", ""));
                        JsonArray t_videos=tlist.getAsJsonArray("videos");
                        if(t_videos != null ){
                            for (int j = 0; j < t_videos.size(); j++) {
                                JsonObject images1 = t_videos.get(j).getAsJsonObject();
                                String admin_video_id = images1.get("admin_video_id").toString().replaceAll("\"", "");
                                String title = images1.get("title").toString().replaceAll("\"", "");
                                String description = images1.get("description").toString().replaceAll("\"", "");
                                String duration = images1.get("duration").toString().replaceAll("\"", "");
                                String third_image = images1.get("third_image").toString().replaceAll("\"", "");
                                String category_name = images1.get("category_name").toString().replaceAll("\"", "");
                                String default_image = images1.get("default_image").toString().replaceAll("\"", "");
                                String publish_time = images1.get("publish_time").toString().replaceAll("\"", "");
                                int category_id = Integer.parseInt(images1.get("category_id").toString().replaceAll("\"", ""));
                                int watch_count = Integer.parseInt(images1.get("watch_count").toString().replaceAll("\"", ""));
                                int ratings = Integer.parseInt(images1.get("ratings").toString().replaceAll("\"", ""));
                                HashMap<String, String> Category = new HashMap<>();
                                // Category.put("product_sub_id",product_sub_id);
                                Category.put("admin_video_id",admin_video_id);
                                Category.put("title",title);
                                Category.put("description",description);
                                Category.put("duration",duration);
                                Category.put("third_image",third_image);
                                Category.put("category_name",category_name);
                                Category.put("default_image",default_image);
                                Category.put("publish_time",publish_time);
                                Category.put("category_id", String.valueOf(category_id));
                                Category.put("watch_count", String.valueOf(watch_count));
                                Category.put("ratings", String.valueOf(ratings));
                                wish_list.add(Category);
                                // Array_category.add(Category);


                            }
/////////////////////////////////////Adapter
                        }

                        JsonObject history=data.getAsJsonObject("history");
                        String hname = history.get("name").getAsString().replaceAll("\"", "");
                        String hkey = history.get("key").getAsString().replaceAll("\"", "");
                        JsonObject hlist=history.getAsJsonObject("list");
                        int h_totalvideo = Integer.parseInt(hlist.get("totalvideo").toString().replaceAll("\"", ""));
                        JsonArray h_videos=hlist.getAsJsonArray("videos");
                        if(t_videos != null ){
                            for (int j = 0; j < h_videos.size(); j++) {
                                JsonObject images1 = h_videos.get(j).getAsJsonObject();
                                String admin_video_id = images1.get("admin_video_id").toString().replaceAll("\"", "");
                                String title = images1.get("title").toString().replaceAll("\"", "");
                                String description = images1.get("description").toString().replaceAll("\"", "");
                                String duration = images1.get("duration").toString().replaceAll("\"", "");
                                String third_image = images1.get("third_image").toString().replaceAll("\"", "");
                                String category_name = images1.get("category_name").toString().replaceAll("\"", "");
                                String default_image = images1.get("default_image").toString().replaceAll("\"", "");
                                String publish_time = images1.get("publish_time").toString().replaceAll("\"", "");
                                int category_id = Integer.parseInt(images1.get("category_id").toString().replaceAll("\"", ""));
                                int watch_count = Integer.parseInt(images1.get("watch_count").toString().replaceAll("\"", ""));
                                int ratings = Integer.parseInt(images1.get("ratings").toString().replaceAll("\"", ""));
                                HashMap<String, String> Category = new HashMap<>();
                                // Category.put("product_sub_id",product_sub_id);
                                Category.put("admin_video_id",admin_video_id);
                                Category.put("title",title);
                                Category.put("description",description);
                                Category.put("duration",duration);
                                Category.put("third_image",third_image);
                                Category.put("category_name",category_name);
                                Category.put("default_image",default_image);
                                Category.put("publish_time",publish_time);
                                Category.put("category_id", String.valueOf(category_id));
                                Category.put("watch_count", String.valueOf(watch_count));
                                Category.put("ratings", String.valueOf(ratings));
                                wish_list.add(Category);
                                // Array_category.add(Category);


                            }
/////////////////////////////////////Adapter
                        }


                        JsonObject suggestion=data.getAsJsonObject("suggestion");
                        String sname = suggestion.get("name").getAsString().replaceAll("\"", "");
                        String skey = suggestion.get("key").getAsString().replaceAll("\"", "");
                        JsonObject slist=suggestion.getAsJsonObject("list");
                        int s_totalvideo = Integer.parseInt(slist.get("totalvideo").toString().replaceAll("\"", ""));
                        JsonArray s_videos=slist.getAsJsonArray("videos");
                        if(s_videos != null ){
                            for (int j = 0; j < s_videos.size(); j++) {
                                JsonObject images1 = s_videos.get(j).getAsJsonObject();
                                String admin_video_id = images1.get("admin_video_id").toString().replaceAll("\"", "");
                                String title = images1.get("title").toString().replaceAll("\"", "");
                                String description = images1.get("description").toString().replaceAll("\"", "");
                                String duration = images1.get("duration").toString().replaceAll("\"", "");
                                String third_image = images1.get("third_image").toString().replaceAll("\"", "");
                                String category_name = images1.get("category_name").toString().replaceAll("\"", "");
                                String default_image = images1.get("default_image").toString().replaceAll("\"", "");
                                String publish_time = images1.get("publish_time").toString().replaceAll("\"", "");
                                int category_id = Integer.parseInt(images1.get("category_id").toString().replaceAll("\"", ""));
                                int watch_count = Integer.parseInt(images1.get("watch_count").toString().replaceAll("\"", ""));
                                int ratings = Integer.parseInt(images1.get("ratings").toString().replaceAll("\"", ""));
                                HashMap<String, String> Category = new HashMap<>();
                                // Category.put("product_sub_id",product_sub_id);
                                Category.put("admin_video_id",admin_video_id);
                                Category.put("title",title);
                                Category.put("description",description);
                                Category.put("duration",duration);
                                Category.put("third_image",third_image);
                                Category.put("category_name",category_name);
                                Category.put("default_image",default_image);
                                Category.put("publish_time",publish_time);
                                Category.put("category_id", String.valueOf(category_id));
                                Category.put("watch_count", String.valueOf(watch_count));
                                Category.put("ratings", String.valueOf(ratings));
                                wish_list.add(Category);


                            }
/////////////////////////////////////Adapter
                        }
                        JsonObject most_viewed=data.getAsJsonObject("most_viewed");
                        String mname = most_viewed.get("name").getAsString().replaceAll("\"", "");
                        String mkey = most_viewed.get("key").getAsString().replaceAll("\"", "");
                        JsonObject mlist=most_viewed.getAsJsonObject("list");
                        int m_totalvideo = Integer.parseInt(mlist.get("totalvideo").toString().replaceAll("\"", ""));
                        JsonArray m_videos=mlist.getAsJsonArray("videos");
                        if(m_videos != null ){
                            for (int j = 0; j < m_videos.size(); j++) {
                                JsonObject images1 = m_videos.get(j).getAsJsonObject();
                                String admin_video_id = images1.get("admin_video_id").toString().replaceAll("\"", "");
                                String title = images1.get("title").toString().replaceAll("\"", "");
                                String description = images1.get("description").toString().replaceAll("\"", "");
                                String duration = images1.get("duration").toString().replaceAll("\"", "");
                                String third_image = images1.get("third_image").toString().replaceAll("\"", "");
                                String category_name = images1.get("category_name").toString().replaceAll("\"", "");
                                String default_image = images1.get("default_image").toString().replaceAll("\"", "");
                                String publish_time = images1.get("publish_time").toString().replaceAll("\"", "");
                                int category_id = Integer.parseInt(images1.get("category_id").toString().replaceAll("\"", ""));
                                int watch_count = Integer.parseInt(images1.get("watch_count").toString().replaceAll("\"", ""));
                                int ratings = Integer.parseInt(images1.get("ratings").toString().replaceAll("\"", ""));
                                HashMap<String, String> Category = new HashMap<>();
                                // Category.put("product_sub_id",product_sub_id);
                                Category.put("admin_video_id",admin_video_id);
                                Category.put("title",title);
                                Category.put("description",description);
                                Category.put("duration",duration);
                                Category.put("third_image",third_image);
                                Category.put("category_name",category_name);
                                Category.put("default_image",default_image);
                                Category.put("publish_time",publish_time);
                                Category.put("category_id", String.valueOf(category_id));
                                Category.put("watch_count", String.valueOf(watch_count));
                                Category.put("ratings", String.valueOf(ratings));
                                wish_list.add(Category);

                            }
/////////////////////////////////////Adapter
                        }

                    }
                    else {

                        Toast.makeText(getActivity(), "message", Toast.LENGTH_LONG).show();
                    }


                    Log.d(TAG,"Token \n" +"message");



                }catch (Exception e) {

                    Toast.makeText(getActivity(),"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Connection Error", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("user_id", globalClass.getId());
                Log.d(TAG, "login param: "+params);
                return params;
            }


        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
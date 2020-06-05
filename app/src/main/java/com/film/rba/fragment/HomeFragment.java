package com.film.rba.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.film.rba.activities.ShowMoreActivity;
import com.film.rba.adapter.BannerAdapter;
import com.film.rba.adapter.MostViewedAdapter;
import com.film.rba.adapter.RecommendedAdapter;
import com.film.rba.adapter.TrendingAdapter;

import com.film.rba.model.Video;
import com.film.rba.networkService.AppConfig;
import com.film.rba.util.GlobalClass;
import com.film.rba.util.Shared_Preference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment  {

    private GlobalClass globalClass;
    private Shared_Preference preference;

    private RelativeLayout rel_recent_views, rel_most_watched, rel_trending, rel_wishlist, rel_popular_rba;
    private TextView show_more_recent, tv_most_watched, tv_show_trending, tv_show_wishlist, tv_show_popular;
    private RecyclerView recycler1, recycler2, recycler3, recycler4, recycler5;
    private TextView txt_movies, txt_videos, txt_tv_shows;

    private BannerAdapter topScrollAdapter;
    private ArrayList<HashMap<String,String>> banner_list;
    private ArrayList<Video> wish_ArrayList, trending_ArrayList, history_ArrayList, suggestion_ArrayList, most_watches_ArrayList;
    private ArrayList<HashMap<String, String>> titlesList;

    private ViewPager viewPager;
    private static int NUM_PAGES = 5;
    private Runnable Update;
    private int currentPage = 0;
    private Timer swipeTimer;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);




        return view;
    }

    private void initViews(View view){
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();


        recycler1 = view.findViewById(R.id.recycler1);// recent views // history
        recycler2 = view.findViewById(R.id.recycler2);// most watches // suggestion
        recycler3 = view.findViewById(R.id.recycler3);// trending
        recycler4 = view.findViewById(R.id.recycler4);// wish list
        recycler5 = view.findViewById(R.id.recycler5);// popular on rba // most viewed

        show_more_recent = view.findViewById(R.id.show_more_recent);
        tv_most_watched = view.findViewById(R.id.tv_most_watched);
        tv_show_popular = view.findViewById(R.id.tv_show_popular);
        tv_show_trending = view.findViewById(R.id.tv_show_trending);
        tv_show_wishlist = view.findViewById(R.id.tv_show_wishlist);

        rel_recent_views = view.findViewById(R.id.rel_recent_views);
        rel_most_watched = view.findViewById(R.id.rel_most_watched);
        rel_trending = view.findViewById(R.id.rel_trending);
        rel_wishlist = view.findViewById(R.id.rel_wishlist);
        rel_popular_rba = view.findViewById(R.id.rel_popular_rba);


        rel_recent_views.setVisibility(View.GONE);
        rel_most_watched.setVisibility(View.GONE);
        rel_trending.setVisibility(View.GONE);
        rel_wishlist.setVisibility(View.GONE);
        rel_popular_rba.setVisibility(View.GONE);


        viewPager = view.findViewById(R.id.viewPager);

        txt_movies = view.findViewById(R.id.txt_movies);
        txt_videos = view.findViewById(R.id.txt_videos);
        txt_tv_shows = view.findViewById(R.id.txt_tv_shows);


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler1.setLayoutManager(layoutManager1);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler2.setLayoutManager(layoutManager2);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler3.setLayoutManager(layoutManager3);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler4.setLayoutManager(layoutManager4);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler5.setLayoutManager(layoutManager5);


        banner_list = new ArrayList<>();
        wish_ArrayList = new ArrayList<>();
        trending_ArrayList = new ArrayList<>();
        history_ArrayList = new ArrayList<>();
        suggestion_ArrayList = new ArrayList<>();
        most_watches_ArrayList = new ArrayList<>();

        titlesList = new ArrayList<>();


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



        show_more_recent.setOnClickListener(v -> {
            Intent show_more=new Intent(getActivity(), ShowMoreActivity.class);
            show_more.putExtra("data", titlesList.get(0));
            startActivity(show_more);
        });
        tv_most_watched.setOnClickListener(v -> {
            Intent show_more=new Intent(getActivity(), ShowMoreActivity.class);
            show_more.putExtra("data", titlesList.get(1));
            startActivity(show_more);
        });
        tv_show_trending.setOnClickListener(v ->{
            Intent show_more=new Intent(getActivity(), ShowMoreActivity.class);
            show_more.putExtra("data", titlesList.get(2));
            startActivity(show_more);
        });
        tv_show_wishlist.setOnClickListener(v -> {
            Intent show_more=new Intent(getActivity(), ShowMoreActivity.class);
            show_more.putExtra("data", titlesList.get(3));
            startActivity(show_more);
        });
        tv_show_popular.setOnClickListener(v -> {
            Intent show_more=new Intent(getActivity(), ShowMoreActivity.class);
            show_more.putExtra("data", titlesList.get(4));
            startActivity(show_more);
        });

        txt_movies.setOnClickListener(v -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("title", txt_movies.getText().toString());
            hashMap.put("key", "movie");

            Intent show_more=new Intent(getActivity(), ShowMoreActivity.class);
            show_more.putExtra("data", hashMap);
            startActivity(show_more);
        });
        txt_videos.setOnClickListener(v -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("title", txt_videos.getText().toString());
            hashMap.put("key", "videos");

            Intent show_more=new Intent(getActivity(), ShowMoreActivity.class);
            show_more.putExtra("data", hashMap);
            startActivity(show_more);

        });
        txt_tv_shows.setOnClickListener(v -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("title", txt_tv_shows.getText().toString());
            hashMap.put("key", "tv_shows");

            Intent show_more=new Intent(getActivity(), ShowMoreActivity.class);
            show_more.putExtra("data", hashMap);
            startActivity(show_more);

        });


        getHomeData();
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

    @Override
    public void onResume() {
        if (banner_list.size() > 0){
            initViewPager(banner_list);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(null);
        swipeTimer.cancel();
        swipeTimer = null;
        super.onPause();
    }

    private void getHomeData() {
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.home, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "home Response: " + response.toString());

                try {

                    JSONObject main_object = new JSONObject(response);

                    boolean status = main_object.optBoolean("success");
                    if(status) {

                        JSONObject data = main_object.getJSONObject("data");
                        JSONObject banner = data.getJSONObject("banner");
                        String name = banner.optString("name");
                        String key = banner.optString("key");

                        JSONArray categories = banner.getJSONArray("list");
                        if(categories != null ){
                            for (int j = 0; j < categories.length(); j++) {
                                JSONObject banners = categories.getJSONObject(j);
                                String admin_video_id = banners.optString("admin_video_id");
                                String title = banners.optString("title");
                                String default_image = banners.optString("default_image");
                                String ratings = banners.optString("ratings");

                                HashMap<String, String> Category = new HashMap<>();
                                Category.put("admin_video_id", admin_video_id);
                                Category.put("title", title);
                                Category.put("default_image", AppConfig.image_url + default_image);
                                Category.put("ratings", ratings);
                                banner_list.add(Category);
                                initViewPager(banner_list);

                            }

                        }


                        /// History ...

                        JSONObject history = data.getJSONObject("history");
                        String hname = history.optString("name");
                        String hkey = history.optString("key");

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("title", hname);
                        hashMap.put("key", hkey);
                        titlesList.add(hashMap);

                        JSONObject hlist = history.getJSONObject("list");
                        int h_totalvideo = hlist.optInt("totalvideo");
                        if (h_totalvideo > 0){
                            rel_recent_views.setVisibility(View.VISIBLE);
                        }
                        JSONArray h_videos = hlist.getJSONArray("videos");
                        if(h_videos != null ){
                            for (int j = 0; j < h_videos.length(); j++) {
                                JSONObject object = h_videos.getJSONObject(j);

                                Video video = new Video();
                                video.setId(object.optString("admin_video_id"));
                                video.setTitle(object.optString("title"));
                                video.setDescription(object.optString("description"));
                                video.setThird_image(AppConfig.image_url+object.optString("third_image"));
                                video.setDuration(object.optString("duration"));
                                video.setRatings(object.optString("ratings"));
                                video.setDefault_image(AppConfig.image_url+object.optString("default_image"));
                                video.setCategory_id(object.optString("category_id"));
                                video.setCategory_name(object.optString("category_name"));
                                video.setWatch_count(object.optString("watch_count"));
                                video.setPublish_time(object.optString("publish_time"));

                                history_ArrayList.add(video);

                            }
                        }


                        /// Most watches ...
                        JSONObject most_viewed = data.getJSONObject("most_viewed");
                        String mname = most_viewed.optString("name");
                        String mkey = most_viewed.optString("key");

                        hashMap = new HashMap<>();
                        hashMap.put("title", mname);
                        hashMap.put("key", mkey);
                        titlesList.add(hashMap);


                        JSONObject mlist = most_viewed.getJSONObject("list");
                        int m_totalvideo = mlist.optInt("totalvideo");
                        if (m_totalvideo > 0){
                            rel_most_watched.setVisibility(View.VISIBLE);
                        }
                        JSONArray m_videos = mlist.getJSONArray("videos");
                        if(m_videos != null ){
                            for (int j = 0; j < m_videos.length(); j++) {
                                JSONObject object = m_videos.getJSONObject(j);

                                Video video = new Video();
                                video.setId(object.optString("admin_video_id"));
                                video.setTitle(object.optString("title"));
                                video.setDescription(object.optString("description"));
                                video.setThird_image(AppConfig.image_url+object.optString("third_image"));
                                video.setDuration(object.optString("duration"));
                                video.setRatings(object.optString("ratings"));
                                video.setDefault_image(AppConfig.image_url+object.optString("default_image"));
                                video.setCategory_id(object.optString("category_id"));
                                video.setCategory_name(object.optString("category_name"));
                                video.setWatch_count(object.optString("watch_count"));
                                video.setPublish_time(object.optString("publish_time"));

                                most_watches_ArrayList.add(video);
                            }
                        }


                        /// Trending ...
                        JSONObject trending = data.getJSONObject("trending");
                        String tname = trending.optString("name");
                        String tkey = trending.optString("key");

                        hashMap = new HashMap<>();
                        hashMap.put("title", tname);
                        hashMap.put("key", tkey);
                        titlesList.add(hashMap);


                        JSONObject tlist = trending.getJSONObject("list");
                        int t_totalvideo = tlist.optInt("totalvideo");
                        if (t_totalvideo > 0){
                            rel_trending.setVisibility(View.VISIBLE);
                        }
                        JSONArray t_videos = tlist.getJSONArray("videos");
                        if(t_videos != null){
                            for (int j = 0; j < t_videos.length(); j++) {
                                JSONObject object = t_videos.getJSONObject(j);

                                Video video = new Video();
                                video.setId(object.optString("admin_video_id"));
                                video.setTitle(object.optString("title"));
                                video.setDescription(object.optString("description"));
                                video.setThird_image(AppConfig.image_url+object.optString("third_image"));
                                video.setDuration(object.optString("duration"));
                                video.setRatings(object.optString("ratings"));
                                video.setDefault_image(AppConfig.image_url+object.optString("default_image"));
                                video.setCategory_id(object.optString("category_id"));
                                video.setCategory_name(object.optString("category_name"));
                                video.setWatch_count(object.optString("watch_count"));
                                video.setPublish_time(object.optString("publish_time"));

                                trending_ArrayList.add(video);
                            }
                        }


                        // Wishlist ...
                        JSONObject wishlist = data.getJSONObject("wishlist");
                        String wname = wishlist.optString("name");
                        String wkey = wishlist.optString("key");

                        hashMap = new HashMap<>();
                        hashMap.put("title", wname);
                        hashMap.put("key", wkey);
                        titlesList.add(hashMap);


                        JSONObject list = wishlist.getJSONObject("list");
                        int totalvideo = list.optInt("totalvideo");
                        if (totalvideo > 0){
                            rel_wishlist.setVisibility(View.VISIBLE);
                        }
                        JSONArray videos = list.getJSONArray("videos");
                        if(videos != null ){
                            for (int j = 0; j < videos.length(); j++) {
                                JSONObject object = videos.getJSONObject(j);

                                Video video = new Video();
                                video.setId(object.optString("admin_video_id"));
                                video.setTitle(object.optString("title"));
                                video.setDescription(object.optString("description"));
                                video.setThird_image(AppConfig.image_url+object.optString("third_image"));
                                video.setDuration(object.optString("duration"));
                                video.setRatings(object.optString("ratings"));
                                video.setDefault_image(AppConfig.image_url+object.optString("default_image"));
                                video.setCategory_id(object.optString("category_id"));
                                video.setCategory_name(object.optString("category_name"));
                                video.setWatch_count(object.optString("watch_count"));
                                video.setPublish_time(object.optString("publish_time"));

                                wish_ArrayList.add(video);

                            }

                        }



                        /// Popular on RBA ...
                        JSONObject suggestion = data.getJSONObject("suggestion");
                        String sname = suggestion.optString("name");
                        String skey = suggestion.optString("key");

                        hashMap = new HashMap<>();
                        hashMap.put("title", sname);
                        hashMap.put("key", skey);
                        titlesList.add(hashMap);


                        JSONObject slist = suggestion.getJSONObject("list");
                        int s_totalvideo = slist.optInt("totalvideo");
                        if (s_totalvideo > 0){
                            rel_popular_rba.setVisibility(View.VISIBLE);
                        }
                        JSONArray s_videos = slist.getJSONArray("videos");
                        if(s_videos != null ){
                            for (int j = 0; j < s_videos.length(); j++) {
                                JSONObject object = s_videos.getJSONObject(j);

                                Video video = new Video();
                                video.setId(object.optString("admin_video_id"));
                                video.setTitle(object.optString("title"));
                                video.setDescription(object.optString("description"));
                                video.setThird_image(AppConfig.image_url+object.optString("third_image"));
                                video.setDuration(object.optString("duration"));
                                video.setRatings(object.optString("ratings"));
                                video.setDefault_image(AppConfig.image_url+object.optString("default_image"));
                                video.setCategory_id(object.optString("category_id"));
                                video.setCategory_name(object.optString("category_name"));
                                video.setWatch_count(object.optString("watch_count"));
                                video.setPublish_time(object.optString("publish_time"));

                                suggestion_ArrayList.add(video);
                            }
                        }


                        setAdapters();

                    } else {
                        Toast.makeText(getActivity(), "message", Toast.LENGTH_LONG).show();
                    }
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
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }

    private void setAdapters(){

        RecommendedAdapter recommendedAdapter =
                new RecommendedAdapter(history_ArrayList, getActivity());
        recycler1.setAdapter(recommendedAdapter);

        MostViewedAdapter mostViewedAdapter =
                new MostViewedAdapter(most_watches_ArrayList, getActivity());
        recycler2.setAdapter(mostViewedAdapter);

        TrendingAdapter trendingAdapter =
                new TrendingAdapter(trending_ArrayList, getActivity());
        recycler3.setAdapter(trendingAdapter);

        RecommendedAdapter recommendedAdapter2 =
                new RecommendedAdapter(wish_ArrayList, getActivity());
        recycler4.setAdapter(recommendedAdapter2);

        RecommendedAdapter recommendedAdapter3 =
                new RecommendedAdapter(suggestion_ArrayList, getActivity());
        recycler5.setAdapter(recommendedAdapter3);

    }

}
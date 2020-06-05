package com.film.rba.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.film.rba.R;
import com.film.rba.activities.ShowMoreActivity;
import com.film.rba.adapter.EventAdapter;
import com.film.rba.adapter.MostViewedAdapter;
import com.film.rba.adapter.SearchAdapter;
import com.film.rba.model.Event;
import com.film.rba.model.Video;
import com.film.rba.networkService.AppConfig;
import com.film.rba.util.GlobalClass;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SearchFragment extends Fragment  {

    private EditText edt_search;
    private RecyclerView recycler2;
    private SearchAdapter searchAdapter;
    private GlobalClass globalClass;
    private ArrayList<Video> videoArrayList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        globalClass = (GlobalClass) getActivity().getApplicationContext();

        edt_search = view.findViewById(R.id.edt_search);
        recycler2 = view.findViewById(R.id.recycler2);
        videoArrayList = new ArrayList<>();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                3,LinearLayoutManager.VERTICAL,false);
        recycler2.setLayoutManager(gridLayoutManager);
        searchAdapter = new SearchAdapter(videoArrayList, getActivity());
        recycler2.setAdapter(searchAdapter);

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0){

                    getSearchVideo(s.toString());
                }

            }
        });


        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

    }


    private void getSearchVideo(String key) {
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.search_video, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "search_video Response: " + response.toString());

                try {
                    videoArrayList.clear();

                    JSONObject main_object = new JSONObject(response);

                    boolean status = main_object.optBoolean("status");
                    if(status) {

                        JSONArray videos = main_object.getJSONArray("video");
                        if(videos != null ){
                            for (int j = 0; j < videos.length(); j++) {
                                JSONObject object = videos.getJSONObject(j);

                                Video video = new Video();
                                video.setId(object.optString("video_id"));
                                video.setTitle(object.optString("title"));
                                video.setDescription(object.optString("description"));
                                video.setThird_image(AppConfig.image_url+object.optString("banner_image"));
                                video.setDuration(object.optString("duration"));
                                video.setRatings(object.optString("ratings"));
                                video.setDefault_image(AppConfig.image_url+object.optString("default_image"));
                                video.setCategory_id(object.optString("category_id"));
                                video.setCategory_name(object.optString("category_name"));
                                video.setWatch_count(object.optString("watch_count"));
                                video.setPublish_time(object.optString("publish_time"));

                                videoArrayList.add(video);
                            }
                        }

                        JSONArray tvshow = main_object.optJSONArray("tvshow");

                        for (int j = 0; j < tvshow.length(); j++) {
                            JSONObject object = tvshow.getJSONObject(j);

                            Video video = new Video();
                            video.setId(object.optString("id"));
                            video.setTitle(object.optString("title"));
                            video.setDescription(object.optString("description"));
                            video.setThird_image(AppConfig.image_url+object.optString("image"));
                            video.setDefault_image(AppConfig.image_url+object.optString("image"));

                            videoArrayList.add(video);
                        }


                        searchAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getActivity(), "No video found", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e) {
                    Toast.makeText(getActivity(),"Some error found", Toast.LENGTH_LONG).show();
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
                params.put("keyword", key);
                Log.d(TAG, "param: "+params);
                return params;
            }
        };
        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }


}
package com.film.rba.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.film.rba.R;
import com.film.rba.adapter.EventAdapter;
import com.film.rba.model.Video;
import com.film.rba.networkService.AppConfig;
import com.film.rba.util.GlobalClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ShowMoreActivity extends AppCompatActivity {
    private ArrayList<Video> videoArrayList;
    private RecyclerView recyclerView;
    private ImageView img_back;
    private TextView tv_title;

    private GlobalClass globalClass;
    HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more);

        recyclerView=findViewById(R.id.recycler2);
        img_back=findViewById(R.id.img_back);
        tv_title=findViewById(R.id.tv_title);
        videoArrayList = new ArrayList<>();

        globalClass = (GlobalClass) getApplicationContext();

        img_back.setOnClickListener(v -> {
            finish();
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            hashMap = (HashMap<String, String>) bundle.getSerializable("data");
            tv_title.setText(hashMap.get("title"));

        }


        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getApplicationContext(),3,
                        LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        getShowMoreData(hashMap.get("key"));

    }


    private void getShowMoreData(String key) {
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.showMore, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "showMore Response: " + response.toString());

                try {

                    JSONObject main_object = new JSONObject(response);

                    boolean status = main_object.optBoolean("success");
                    if(status) {

                        JSONObject data = main_object.getJSONObject("data");
                        int totalvideo = data.optInt("totalvideo");

                        JSONArray videos = data.getJSONArray("videos");
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

                                videoArrayList.add(video);
                            }
                        }

                        EventAdapter eventAdapter =
                                new EventAdapter(videoArrayList, ShowMoreActivity.this);
                        recyclerView.setAdapter(eventAdapter);

                    } else {
                        Toast.makeText(getApplicationContext(), "message", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Some error found", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Connection Error", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("user_id", globalClass.getId());
                params.put("key", key);
                Log.d(TAG, "login param: "+params);
                return params;
            }
        };
        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }

}

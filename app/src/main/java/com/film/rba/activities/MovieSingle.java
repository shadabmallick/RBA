package com.film.rba.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.skyhope.showmoretextview.ShowMoreTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MovieSingle extends AppCompatActivity {
    RecyclerView recycler_starring,recycler_like;
    JWPlayerView mPlayerView;
    private ArrayList<Video> videoArrayList;

    LinearLayout linear_main;
    ImageView img_back;
    TextView tv_video_title, tv_genre_name, tv_duration, tv_release_date, tv_director;
    ShowMoreTextView tv_description;
    String video_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);

        recycler_starring=findViewById(R.id.recycler1);
        recycler_like=findViewById(R.id.recycler2);
        img_back=findViewById(R.id.img_back);
        linear_main=findViewById(R.id.linear_main);
        tv_video_title=findViewById(R.id.tv_video_title);
        tv_genre_name=findViewById(R.id.tv_genre_name);
        tv_duration=findViewById(R.id.tv_duration);
        tv_release_date=findViewById(R.id.tv_release_date);
        tv_director=findViewById(R.id.tv_director);
        tv_description=findViewById(R.id.tv_description);
        tv_description.setShowingLine(4);
        tv_description.addShowLessText("Less");
        tv_description.addShowMoreText("More");

        linear_main.setVisibility(View.GONE);

        videoArrayList = new ArrayList<>();

        mPlayerView = findViewById(R.id.jwplayer);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recycler_like.setLayoutManager(layoutManager1);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            video_id = bundle.getString("id");
            getVideoDetails("");
        }


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mPlayerView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlayerView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.onDestroy();
    }


    private void getVideoDetails(String key) {
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.videodetails, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "showMore Response: " + response.toString());

                try {

                    linear_main.setVisibility(View.VISIBLE);

                    JSONObject main_object = new JSONObject(response);

                    boolean status = main_object.optBoolean("success");
                    if(status) {

                        String wishlist_status = main_object.optString("wishlist_status");
                        String history_status = main_object.optString("history_status");
                        String share_link = main_object.optString("share_link");
                        String main_video = AppConfig.image_url+main_object.optString("main_video");
                        String tralier_video = AppConfig.image_url+main_object.optString("tralier_video");
                        String ios_video = AppConfig.image_url+main_object.optString("ios_video");
                        String ios_tralier_video = AppConfig.image_url+main_object.optString("ios_tralier_video");

                        JSONObject video_obj = main_object.getJSONObject("video");

                        String director = video_obj.optString("director");
                        String price = video_obj.optString("price");
                        String title = video_obj.optString("title");
                        String description = video_obj.optString("description");
                        String type = video_obj.optString("type");
                        String ratings = video_obj.optString("ratings");
                        String reviews = video_obj.optString("reviews");
                        String video_date = video_obj.optString("video_date");
                        String video_str = AppConfig.image_url+video_obj.optString("video");
                        String trailer_video = AppConfig.image_url+video_obj.optString("trailer_video");
                        String default_image = AppConfig.image_url+video_obj.optString("default_image");
                        String watch_count = video_obj.optString("watch_count");
                        String video_type = video_obj.optString("video_type");
                        String video_upload_type = video_obj.optString("video_upload_type");
                        String duration = video_obj.optString("duration");
                        String publish_time = video_obj.optString("publish_time");
                        String category_name = video_obj.optString("category_name");
                        String genre_name = video_obj.optString("genre_name");


                        tv_video_title.setText(title);
                        tv_description.setText(description);
                        tv_director.setText(director);
                        tv_duration.setText(duration);
                        tv_genre_name.setText(genre_name);
                        tv_release_date.setText(publish_time);

                        playVideo(tralier_video, default_image);


                        JSONArray video_images = main_object.getJSONArray("video_images");
                        for (int j = 0; j < video_images.length(); j++) {
                            JSONObject object = video_images.getJSONObject(j);
                            

                        }


                        JSONArray you_may_like = main_object.getJSONArray("you_may_like");
                        if(you_may_like != null ){
                            for (int j = 0; j < you_may_like.length(); j++) {
                                JSONObject object = you_may_like.getJSONObject(j);

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
                                new EventAdapter(videoArrayList, MovieSingle.this);
                        recycler_like.setAdapter(eventAdapter);

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

                params.put("admin_video_id", video_id);
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

    private void playVideo(String video_link, String poster){

        PlaylistItem playlistItem = new PlaylistItem.Builder()
                .file(video_link)
                .image(poster)
                .title(tv_video_title.getText().toString())
                .description(tv_description.getText().toString())
                .build();

        List<PlaylistItem> playlist = new ArrayList<>();
        playlist.add(playlistItem);
        PlayerConfig config = new PlayerConfig.Builder()
                .playlist(playlist)
                .build();
        mPlayerView.setup(config);

    }

}
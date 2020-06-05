package com.film.rba.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.film.rba.R;
import com.film.rba.activities.UpdateProfile;
import com.film.rba.adapter.ContinueWatchingAdapter;
import com.film.rba.adapter.RecommendedAdapter;
import com.film.rba.adapter.WatchListAdapter;
import com.film.rba.model.Video;
import com.film.rba.networkService.AppConfig;
import com.film.rba.util.GlobalClass;
import com.film.rba.util.Shared_Preference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.film.rba.networkService.AppConfig.RETRY;
import static com.film.rba.networkService.AppConfig.TIMEOUT;
import static com.film.rba.networkService.AppConfig.backoffMultiplier;

public class ProfileFragment extends Fragment {

    private LinearLayout rl_edit_profile;
    private CircularImageView img_profile;
    private TextView txt_name;
    private RecyclerView recyclerView1,recyclerView2,recyclerView3;

    private GlobalClass globalClass;
    private Shared_Preference preference;
    private ProgressDialog progressDialog;

    private ArrayList<Video> continue_watch_ArrayList;
    private ArrayList<Video> watch_ArrayList;
    private ArrayList<Video> recommended_watch_ArrayList;
    private LinearLayout ll_1, ll_2, ll_3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(view);

        return view;
    }

    private void initViews(View view){

        recyclerView1=view.findViewById(R.id.recycler1);
        recyclerView2=view.findViewById(R.id.recycler2);
        recyclerView3=view.findViewById(R.id.recycler3);
        img_profile=view.findViewById(R.id.img_profile);
        rl_edit_profile=view.findViewById(R.id.rl_edit_profile);
        txt_name=view.findViewById(R.id.txt_name);
        ll_1=view.findViewById(R.id.ll_1);
        ll_2=view.findViewById(R.id.ll_2);
        ll_3=view.findViewById(R.id.ll_3);
        ll_1.setVisibility(View.GONE);
        ll_2.setVisibility(View.GONE);
        ll_3.setVisibility(View.GONE);

        LinearLayoutManager horizontal1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(horizontal1);

        LinearLayoutManager horizontal2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(horizontal2);

        LinearLayoutManager horizontal3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(horizontal3);


        preference = new Shared_Preference(getActivity());
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference.loadPrefrence();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));

        Picasso.get().load(globalClass.getProfil_pic()).into(img_profile);
        txt_name.setText(globalClass.getName());


        rl_edit_profile.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), UpdateProfile.class);
            startActivity(intent);

        });

        getProfile();
    }

    @Override
    public void onResume() {

        try {
            Picasso.get().load(globalClass.getProfil_pic()).into(img_profile);
            txt_name.setText(globalClass.getName());
        }catch (Exception e){
            e.printStackTrace();
        }


        super.onResume();
    }

    private void getProfile() {
        // Tag used to cancel the request
        String tag_string_req = "request";

        progressDialog.show();

        continue_watch_ArrayList = new ArrayList<>();
        watch_ArrayList = new ArrayList<>();
        recommended_watch_ArrayList = new ArrayList<>();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.profile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(AppConfig.TAG, "Response: " + response.toString());

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.optBoolean("success");

                    if(status){

                        JSONObject profile = jsonObject.getJSONObject("profile");
                        String picture = AppConfig.image_url+profile.optString("picture");
                        Picasso.get().load(picture).into(img_profile);
                        globalClass.setProfil_pic(picture);

                        JSONObject continue_watching = jsonObject.getJSONObject("continue_watching");
                        String total_video1 = continue_watching.optString("totalvideo");
                        JSONArray videos1 = continue_watching.getJSONArray("videos");

                        if (Integer.parseInt(total_video1) > 0){
                            ll_1.setVisibility(View.VISIBLE);
                        }

                        for (int i = 0; i < videos1.length(); i++){
                            JSONObject object = videos1.getJSONObject(i);

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

                            continue_watch_ArrayList.add(video);
                        }



                        JSONObject watch_list = jsonObject.getJSONObject("watch_list");
                        String total_video2 = watch_list.optString("totalvideo");
                        JSONArray videos2 = watch_list.getJSONArray("videos");
                        if (Integer.parseInt(total_video2) > 0){
                            ll_2.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < videos2.length(); i++){
                            JSONObject object = videos2.getJSONObject(i);

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

                            watch_ArrayList.add(video);
                        }



                        JSONObject recommended_movies = jsonObject.getJSONObject("recommended_movies");
                        String total_video3 = recommended_movies.optString("totalvideo");
                        JSONArray videos3 = recommended_movies.getJSONArray("videos");
                        if (Integer.parseInt(total_video3) > 0){
                            ll_3.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < videos3.length(); i++){
                            JSONObject object = videos3.getJSONObject(i);

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

                            recommended_watch_ArrayList.add(video);
                        }


                        setAdapters();

                    } else {
                        String error = jsonObject.optString("error");
                        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e) {
                    Toast.makeText(getActivity(),"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(AppConfig.TAG, "DATA NOT FOUND: "+error.getMessage());
                Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id", globalClass.getId());
                params.put("token", globalClass.getToken());

                Log.d(AppConfig.TAG, "param: "+params);
                return params;
            }


        };

        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, RETRY, backoffMultiplier));

    }

    private void setAdapters(){

        ContinueWatchingAdapter continueWatchingAdapter =
                new ContinueWatchingAdapter(continue_watch_ArrayList, getActivity());
        recyclerView1.setAdapter(continueWatchingAdapter);

        WatchListAdapter watchListAdapter =
                new WatchListAdapter(watch_ArrayList, getActivity());
        recyclerView2.setAdapter(watchListAdapter);

        RecommendedAdapter recommendedAdapter =
                new RecommendedAdapter(recommended_watch_ArrayList, getActivity());
        recyclerView3.setAdapter(recommendedAdapter);

    }

}
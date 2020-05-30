package com.film.rba.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.film.rba.R;
import com.film.rba.adapter.EventAdapter;
import com.film.rba.adapter.LikeAdapter;
import com.film.rba.adapter.StarringAdapter;
import com.film.rba.model.Event;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;

import java.util.ArrayList;
import java.util.List;

public class MovieSingle extends AppCompatActivity {
    RecyclerView recycler_starring,recycler_like;
    JWPlayerView mPlayerView;
    private List<Event> groceryList1 = new ArrayList<>();
    LinearLayout ll_back;
    StarringAdapter starringAdapter;
    LikeAdapter likeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_movie);
        recycler_starring=findViewById(R.id.recycler1);
        recycler_like=findViewById(R.id.recycler2);
        ll_back=findViewById(R.id.ll_back);
        populategroceryList1();

        mPlayerView = findViewById(R.id.jwplayer);
        PlaylistItem playlistItem = new PlaylistItem.Builder()
                .file("https://cdn.jwplayer.com/manifests/{MEDIA_ID}.m3u8")
                .build();

        List<PlaylistItem> playlist = new ArrayList<>();
        playlist.add(playlistItem);
        PlayerConfig config = new PlayerConfig.Builder()
                .playlist(playlist)
                .build();
        mPlayerView.setup(config);


       /* starringAdapter = new StarringAdapter(groceryList1, getApplicationContext());
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_starring.setLayoutManager(horizontalLayoutManager1);
        recycler_starring.setAdapter(starringAdapter);*/


       /* likeAdapter = new LikeAdapter(groceryList1, getApplicationContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_like.setLayoutManager(horizontalLayoutManager);
        recycler_like.setAdapter(likeAdapter);*/


        ll_back.setOnClickListener(new View.OnClickListener() {
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

}
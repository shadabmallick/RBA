package com.film.rba.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.film.rba.R;
import com.film.rba.activities.MovieSingle;
import com.film.rba.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.MyViewHolder>{
    private ArrayList<Video> videoList;
    Context context;

    public WatchListAdapter(ArrayList<Video> videoList, Context context){
        this.videoList= videoList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_watch_list, parent, false);
        MyViewHolder gvh = new MyViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
          if(position == 0){
              holder.txt_watch.setVisibility(View.VISIBLE);
              holder.imageView.setVisibility(View.GONE);

          } else {
              holder.txt_watch.setVisibility(View.GONE);
              holder.imageView.setVisibility(View.VISIBLE);

              Video video = videoList.get(position);

              Picasso.get().load(video.getDefault_image()).into(holder.imageView);


              holder.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent intent=new Intent(context, MovieSingle.class);
                      intent.putExtra("id", video.getId());
                      context.startActivity(intent);
                  }
              });
          }


    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       RelativeLayout rl_main;
        TextView txt_watch,subtitle,time,title1,title2,title4,title5;
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.imageView1);
            txt_watch=view.findViewById(R.id.txt_show);

        }
    }
}
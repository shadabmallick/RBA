package com.film.rba.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.film.rba.R;
import com.film.rba.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.MyViewHolder>{
    private ArrayList<Video> videoList;
    private Context context;

    public RecommendedAdapter(ArrayList<Video> videoList, Context context){
        this.videoList = videoList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View groceryProductView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_recent, parent, false);
        MyViewHolder gvh = new MyViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Video video = videoList.get(position);

        Picasso.get().load(video.getDefault_image()).into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {


        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.imageView1);
        }
    }
}
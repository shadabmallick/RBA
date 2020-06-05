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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{
    private ArrayList<Video> videoList;
    Context context;

    public SearchAdapter(ArrayList<Video> videoList, Context context){
        this.videoList= videoList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_search, parent, false);
        MyViewHolder gvh = new MyViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Video video = videoList.get(position);

        Picasso.get().load(video.getDefault_image())
                .error(R.mipmap.mov_img1)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MovieSingle.class);
                intent.putExtra("id", video.getId());
                context.startActivity(intent);
            }
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
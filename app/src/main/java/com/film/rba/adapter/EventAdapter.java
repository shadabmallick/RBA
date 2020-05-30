package com.film.rba.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.film.rba.R;
import com.film.rba.activities.MovieSingle;
import com.film.rba.model.Video;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>{

    private ArrayList<Video> wish_list;
    private Context context;

    public EventAdapter(ArrayList<Video> wish_list, Context context){
        this.wish_list= wish_list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_recent, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Video video = wish_list.get(position);


        Picasso.get().load(video.getDefault_image())
                .error(R.mipmap.mov_img1)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MovieSingle.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return wish_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.imageView1);

        }
    }
}
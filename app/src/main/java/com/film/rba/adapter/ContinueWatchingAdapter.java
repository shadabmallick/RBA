package com.film.rba.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.film.rba.R;
import com.film.rba.activities.MovieSingle;
import com.film.rba.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContinueWatchingAdapter extends RecyclerView.Adapter<ContinueWatchingAdapter.MyViewHolder>{
    private ArrayList<Video> videoArrayList;
    private Context context;

    public ContinueWatchingAdapter(ArrayList<Video> videoArrayList, Context context){
        this.videoArrayList = videoArrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_continue_watching, parent, false);
        MyViewHolder gvh = new MyViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Video video = videoArrayList.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }

        Picasso.get().load(video.getDefault_image()).into(holder.imageView);

        holder.tv_name.setText(video.getTitle());

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
        return videoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_duration;
        ImageView imageView;
        ProgressBar progressBar;
        public MyViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.imageView1);
            progressBar=view.findViewById(R.id.progressBar);
            tv_name=view.findViewById(R.id.tv_name);
            tv_duration=view.findViewById(R.id.tv_duration);
        }
    }
}
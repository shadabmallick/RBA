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
import com.film.rba.model.Event;

import java.util.List;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.MyViewHolder>{
    private List<Event> horizontalGrocderyList;
    Context context;

    public LikeAdapter(List<Event> horizontalGrocderyList, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recent, parent, false);
        MyViewHolder gvh = new MyViewHolder(view);
        return gvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.imageView.setImageResource(horizontalGrocderyList.get(position).getProductImage());

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
        return horizontalGrocderyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       RelativeLayout rl_main;
        TextView title,subtitle,time,title1,title2,title4,title5;
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.imageView1);

        }
    }
}
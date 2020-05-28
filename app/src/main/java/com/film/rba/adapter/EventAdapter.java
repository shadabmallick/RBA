package com.film.rba.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.film.rba.R;
import com.film.rba.activities.MovieSingle;
import com.film.rba.model.Event;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.GroceryViewHolder>{

    ArrayList<HashMap<String,String>> wish_list;
    Context context;

    public EventAdapter(ArrayList<HashMap<String,String>> wish_list, Context context){
        this.wish_list= wish_list;
        this.context = context;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recent, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {
      //  holder.imageView.setImageResource(wish_list.get(position).getProductImage());
        Picasso.get().load(wish_list.get(position).get("default_image")).error(R.mipmap.mov_img1).into(holder.imageView);

       /* holder.time.setText(horizontalGrocderyList.get(position).getTime());
        holder.title1.setText(horizontalGrocderyList.get(position).getTitle());
        holder.title2.setText(horizontalGrocderyList.get(position).getSubtitle());
        holder.title4.setText(horizontalGrocderyList.get(position).getTitle1());
        holder.title5.setText(horizontalGrocderyList.get(position).getTitle2());*/

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

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
       RelativeLayout rl_main;
        TextView title,subtitle,time,title1,title2,title4,title5;
        ImageView imageView;
        public GroceryViewHolder(View view) {
            super(view);
          /*  subtitle=view.findViewById(R.id.title1);
            title=view.findViewById(R.id.title2);
            time=view.findViewById(R.id.title3);

            rl_main=view.findViewById(R.id.rl_main);
            title1=view.findViewById(R.id.title1);
            title2=view.findViewById(R.id.title2);*/
            imageView=view.findViewById(R.id.imageView1);
          /*  title4=view.findViewById(R.id.title4);
            title5=view.findViewById(R.id.title5);*/
        }
    }
}
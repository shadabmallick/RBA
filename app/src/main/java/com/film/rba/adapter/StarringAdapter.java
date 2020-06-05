package com.film.rba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.film.rba.R;
import com.film.rba.model.Event;
import com.film.rba.model.Video;

import java.util.ArrayList;
import java.util.List;

public class StarringAdapter extends RecyclerView.Adapter<StarringAdapter.MyViewHolder>{
    private ArrayList<Video> videoArrayList;
    private Context context;

    public StarringAdapter(ArrayList<Video> videoArrayList, Context context){
        this.videoArrayList= videoArrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_starring, parent, false);
        MyViewHolder gvh = new MyViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {




    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.imageView1);

        }
    }
}
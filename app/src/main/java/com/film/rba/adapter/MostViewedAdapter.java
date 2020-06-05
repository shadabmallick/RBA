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

import java.util.List;

public class MostViewedAdapter extends RecyclerView.Adapter<MostViewedAdapter.MyViewHolder>{
    private List<Video> arrayList;
    private Context context;

    public MostViewedAdapter(List<Video> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.most_watch, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Video video = arrayList.get(position);

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
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.imageView1);
            tv_name=view.findViewById(R.id.tv_name);

        }
    }
}
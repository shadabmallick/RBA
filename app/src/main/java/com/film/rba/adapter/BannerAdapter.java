package com.film.rba.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;


import com.film.rba.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class BannerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<HashMap<String,String>> images;
    private LayoutInflater layoutInflater;


    public BannerAdapter(Context context, ArrayList<HashMap<String,String>> images) {
        this.context = context;
        this.images = images;
        this.layoutInflater = LayoutInflater.from(context);

        //layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.single_top,
                container, false);
        Log.d("TAG", "instantiateItem: "+images.get(position).get("default_image"));
        ImageView imageView = itemView.findViewById(R.id.top_image);

        Picasso.get().load(images.get(position).get("default_image")).into(imageView);
        container.addView(itemView);

/*
        if(!(images.get(position).get("link").equals(""))){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context, PageLink.class);
                    intent.putExtra("link",images.get(position).get("link"));
                    context.startActivity(intent);

                }
            });
        }
*/

        /*Glide.with(context).load(images.get(position).get("image"))
                .placeholder(R.mipmap.homebanner)
                .into(imageView);*/



        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
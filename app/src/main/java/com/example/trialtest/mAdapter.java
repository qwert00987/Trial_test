package com.example.trialtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class mAdapter extends RecyclerView.Adapter<mAdapter.ViewHolder> {
    ArrayList<String> urls;
    Context context;


    public mAdapter(ArrayList<String> ImgUrl, Context context_) {
        this.urls = ImgUrl;
        this.context = context_;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView webUrl;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.imageFromURL);
            name = v.findViewById(R.id.webName);
            webUrl = v.findViewById(R.id.webURL);
        }

        public ImageView getImage() {
            return this.image;
        }
    }

    @Override
    public mAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eg_url, parent, false);
        return new ViewHolder(v);
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        Glide.with(this.context)
                .load(urls.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.getImage());

        holder.name.setText(urls.get(position));
        holder.webUrl.setText(urls.get(position));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }
}

package com.example.meme.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.meme.R;
import com.example.meme.Videos;


import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder>{

    private final RecycleViewInterface recycleViewInterface;
    static Context context;
    ArrayList<Videos> videosArrayList;

    public void setVideosArrayList(ArrayList<Videos> videosArrayList) {
        this.videosArrayList = videosArrayList;
    }

    public VideoAdapter(Context context, ArrayList<Videos> videos, RecycleViewInterface recycleViewInterface){
        this.context = context;
        this.videosArrayList = videos;
        this.recycleViewInterface = recycleViewInterface;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.video,parent,false);

        return new MyViewHolder(v,recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Videos video = videosArrayList.get(position);
        holder.setThubnaile(video.thubnail);
        holder.naziv.setText(video.naziv);
        holder.ime.setText(video.kanal);
        //holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {

        return videosArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView thubnaile;

        ImageView icon;
        TextView naziv;

        TextView ime;
        View view;


        public void setThubnaile(String url) {
            thubnaile = view.findViewById(R.id.title_image);
            Glide.with(context).load(url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(thubnaile);
        }

        public MyViewHolder(@NonNull View itemView,RecycleViewInterface recycleViewInterface) {
            super(itemView);
            view = itemView;
            naziv = itemView.findViewById(R.id.naziv);
            ime = itemView.findViewById(R.id.kanal_naziv);

            icon = view.findViewById(R.id.kanal_icon);
            //Glide.with(context).load("http://192.168.1.4:3001/images/profile.png").transform(new CircleCrop()).into(icon);
            

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recycleViewInterface !=null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                        {
                            recycleViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recycleViewInterface.onLink(pos);
                        }
                    }
                }
            });
            ime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recycleViewInterface.onLink(pos);
                        }
                    }
                }
            });
        }

    }
}

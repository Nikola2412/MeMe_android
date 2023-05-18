package com.example.meme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class playerAdapter extends RecyclerView.Adapter<playerAdapter.playerViewHolder> {

    private final playerInterface playerInterface;
    static Context context;
    ArrayList<Videos> videosArrayList;
    String url;

    public playerAdapter(Context context, ArrayList<Videos> videos, playerInterface playerInterface,String url) {
        this.playerInterface = playerInterface;
        this.context = context;
        this.videosArrayList = videos;
        this.url = url;
    }

    @NonNull
    @Override
    public playerAdapter.playerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.video,parent,false);
        return new playerViewHolder(v,playerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull playerAdapter.playerViewHolder holder, int position) {
        Videos video = videosArrayList.get(position);
        holder.setThubnaile(video.thubnail);
        holder.naziv.setText(video.naziv);
        holder.ime.setText(video.kanal);

    }

    @Override
    public int getItemCount() {
        return videosArrayList.size();
    }

    public class playerViewHolder extends RecyclerView.ViewHolder {
        ImageView thubnaile;

        ImageView icon;
        TextView naziv;

        TextView ime;
        View view;
        public void setThubnaile(String url) {
            thubnaile = view.findViewById(R.id.title_image);
            Glide.with(context).load(url).into(thubnaile);
        }
        public playerViewHolder(@NonNull View itemView, playerInterface playerInterface) {
            super(itemView);
            view = itemView;
            naziv = itemView.findViewById(R.id.naziv);
            ime = itemView.findViewById(R.id.kanal_naziv);

            icon = view.findViewById(R.id.kanal_icon);

            Glide.with(context).load("http://192.168.1.9:3001/images/profile.png").into(icon);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(playerInterface !=null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                        {
                            playerInterface.onItemClick(pos);
                        }
                    }
                }
            });
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(playerInterface !=null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                        {
                            playerInterface.onItemClick(pos);
                        }
                    }
                }
            });
            ime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(playerInterface !=null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                        {
                            playerInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}

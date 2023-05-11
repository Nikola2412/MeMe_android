package com.example.meme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<Videos> videosArrayList;

    public MyAdapter(Context context,ArrayList<Videos> videos){
        this.context = context;
        this.videosArrayList = videos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.video,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Videos video = videosArrayList.get(position);
        holder.thubnaile.setImageResource(video.thubnail);
        holder.naziv.setText(video.naziv);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return videosArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView thubnaile;
        TextView naziv;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thubnaile = itemView.findViewById(R.id.title_image);
            naziv = itemView.findViewById(R.id.naziv);
        }
    }
    /*
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public interface OnItemClickListener {
        public void onItemClick(int position);
    }
    */
}

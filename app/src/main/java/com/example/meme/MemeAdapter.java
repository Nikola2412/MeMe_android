package com.example.meme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import com.example.meme.MemeInterface;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.MemeViewHolder> {

    private final MemeInterface memeInterface;
    static Context context;
    ArrayList<Meme> memeArrayList;

    public MemeAdapter(Context context, ArrayList<Meme> memes, MemeInterface memeInterface){
        this.context = context;
        this.memeArrayList = memes;
        this.memeInterface = memeInterface;
    }

    @NonNull
    @Override
    public MemeAdapter.MemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.meme,parent,false);

        return new MemeAdapter.MemeViewHolder(v,memeInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MemeAdapter.MemeViewHolder holder, int position) {
        Meme meme = memeArrayList.get(position);
        holder.setMeme(meme.id);
    }

    @Override
    public int getItemCount() {
        return memeArrayList.size();
    }

    public static class MemeViewHolder extends RecyclerView.ViewHolder {
        ImageView meme;
        View view;

        public void setMeme(String id) {
            meme = view.findViewById(R.id.mim);
            Glide.with(context).load(id).into(meme);
        }

        public MemeViewHolder(@NonNull View itemView, MemeInterface recycleViewInterface) {
            super(itemView);
            view = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recycleViewInterface !=null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                        {
                            //recycleViewInterface.onItemClick(pos);
                            recycleViewInterface.openImageFullscreen(meme,pos);
                        }
                    }
                }
            });

        }
    }
}

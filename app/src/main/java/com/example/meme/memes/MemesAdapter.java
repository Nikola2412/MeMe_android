package com.example.meme.memes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.meme.Meme;
import com.example.meme.R;

import java.util.ArrayList;

public class MemesAdapter extends RecyclerView.Adapter<MemesAdapter.MemeViewHolder> {

    private final MemesInterface memesInterface;
    static Context context;
    ArrayList<Meme> memeArrayList;

    public MemesAdapter(Context context, ArrayList<Meme> memes, MemesInterface memesInterface){
        this.context = context;
        this.memeArrayList = memes;
        this.memesInterface = memesInterface;
    }

    @NonNull
    @Override
    public MemesAdapter.MemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.meme,parent,false);

        return new MemesAdapter.MemeViewHolder(v, memesInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MemesAdapter.MemeViewHolder holder, int position) {
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

        public void setMeme(String path) {
            meme = view.findViewById(R.id.mim);
            Glide.with(context).load(path).into(meme);
        }

        public MemeViewHolder(@NonNull View itemView, MemesInterface recycleViewInterface) {
            super(itemView);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
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

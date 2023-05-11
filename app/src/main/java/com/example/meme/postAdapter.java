package com.example.meme;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class postAdapter extends RecyclerView.Adapter<postAdapter.postHolder> {

    @NonNull
    @Override
    public postHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull postHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class postHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView ime,name;
        View view;

        public postHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
        public void SetImageView(String url){
            imageView = imageView.findViewById(R.id.title_image);
        }

    }


}

package com.example.meme.memes;

import android.widget.ImageView;

public interface MemesInterface {
    void onItemClick(int position);
    void openImageFullscreen(String path,int pos);
}

package com.example.meme;

import android.widget.ImageView;

public interface MemeInterface {
    void onItemClick(int position);
    void openImageFullscreen(ImageView imageView,int pos);
}

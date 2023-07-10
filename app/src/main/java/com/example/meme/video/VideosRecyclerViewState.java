package com.example.meme.video;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.meme.Videos;

import java.util.ArrayList;

public class VideosRecyclerViewState implements Parcelable {
    public ArrayList<Videos> videos;

    public VideosRecyclerViewState(ArrayList<Videos> videos) {
        this.videos = videos;
    }

    public Videos returnVideo(int pos){
        return videos.get(pos);
    }

    protected VideosRecyclerViewState(Parcel in) {
        videos = in.createTypedArrayList(Videos.CREATOR);
    }

    public static final Creator<VideosRecyclerViewState> CREATOR = new Creator<VideosRecyclerViewState>() {
        @Override
        public VideosRecyclerViewState createFromParcel(Parcel in) {
            return new VideosRecyclerViewState(in);
        }

        @Override
        public VideosRecyclerViewState[] newArray(int size) {
            return new VideosRecyclerViewState[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }

    public ArrayList<Videos> getItems() {
        return videos;
    }
}

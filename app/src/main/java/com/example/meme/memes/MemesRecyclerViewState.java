package com.example.meme.memes;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.meme.Meme;

import java.util.ArrayList;

public class MemesRecyclerViewState implements Parcelable {
    private ArrayList<Meme> memes;

    public MemesRecyclerViewState(ArrayList<Meme> memes) {
        this.memes = memes;
    }
    public Meme getMemes(int pos) {
        return memes.get(pos);
    }

    public void addMeme(Meme meme) {
        this.memes.add(meme);
    }

    public ArrayList<Meme> getMemes() {
        return memes;
    }

    protected MemesRecyclerViewState(Parcel in) {
        memes = in.createTypedArrayList(Meme.CREATOR);
    }

    public static final Creator<MemesRecyclerViewState> CREATOR = new Creator<MemesRecyclerViewState>() {
        @Override
        public MemesRecyclerViewState createFromParcel(Parcel in) {
            return new MemesRecyclerViewState(in);
        }

        @Override
        public MemesRecyclerViewState[] newArray(int size) {
            return new MemesRecyclerViewState[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }
}

package com.example.meme.uplaod;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.meme.UploadMeme;

import java.util.ArrayList;

public class UploadRecyclerViewState implements Parcelable {
    private ArrayList<UploadMeme> memes;

    public UploadRecyclerViewState(ArrayList<UploadMeme> memes) {
        this.memes = memes;
    }

    protected UploadRecyclerViewState(Parcel in) {
        memes = in.createTypedArrayList(UploadMeme.CREATOR);
    }

    public static final Creator<UploadRecyclerViewState> CREATOR = new Creator<UploadRecyclerViewState>() {
        @Override
        public UploadRecyclerViewState createFromParcel(Parcel in) {
            return new UploadRecyclerViewState(in);
        }

        @Override
        public UploadRecyclerViewState[] newArray(int size) {
            return new UploadRecyclerViewState[size];
        }
    };

    public ArrayList<UploadMeme> getMemes() {
        return memes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }
}

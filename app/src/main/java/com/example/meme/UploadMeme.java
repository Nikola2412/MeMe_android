package com.example.meme;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UploadMeme implements Parcelable {
    public Uri Path;

    public UploadMeme(Uri path) {
        Path = path;
    }

    protected UploadMeme(Parcel in) {
        Path = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<UploadMeme> CREATOR = new Creator<UploadMeme>() {
        @Override
        public UploadMeme createFromParcel(Parcel in) {
            return new UploadMeme(in);
        }

        @Override
        public UploadMeme[] newArray(int size) {
            return new UploadMeme[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(Path, flags);
    }
}

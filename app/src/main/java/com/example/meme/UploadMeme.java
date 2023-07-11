package com.example.meme;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UploadMeme implements Parcelable {
    public Uri Path;
    public Uri org;//null

    public void setPath(Uri path) {
        Path = path;
    }

    public void orgPath(){
        Path = org;
    }


    public Uri getPath() {
        return Path;
    }

    public Uri getOrg() {
        return org;
    }

    public UploadMeme(Uri path) {
        Path = path;
        org = path;
    }
    public boolean Edited(){
        return org != Path;
    }

    protected UploadMeme(Parcel in) {
        Path = in.readParcelable(Uri.class.getClassLoader());
        org = in.readParcelable(Uri.class.getClassLoader());
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
        dest.writeParcelable(org,flags);
    }
}

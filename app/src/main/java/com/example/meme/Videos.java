package com.example.meme;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class Videos implements Parcelable {

    public String naziv;
    public String thubnail;

    public String link;

    public String kanal;

    public Videos(String naziv, String thubnail, String link, String kanal) {
        this.naziv = naziv;
        this.thubnail = thubnail;
        this.link = link;
        this.kanal = kanal;
    }

    protected Videos(Parcel in) {
        naziv = in.readString();
        thubnail = in.readString();
        link = in.readString();
        kanal = in.readString();
    }

    public static final Creator<Videos> CREATOR = new Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel in) {
            return new Videos(in);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };

    public String getNaziv() {
        return naziv;
    }

    public String getThubnail() {
        return thubnail;
    }

    public String getLink() {
        return link;
    }

    public String getKanal() {
        return kanal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(naziv);
        dest.writeString(thubnail);
        dest.writeString(link);
        dest.writeString(kanal);
    }
}

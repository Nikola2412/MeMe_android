package com.example.meme;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Meme implements Parcelable {
    public String id;
    public int id_kanala;

    public String naziv_kanala;


    public Meme(String id, int id_kanala, String naziv_kanala) {
        this.id = id;
        this.id_kanala = id_kanala;
        this.naziv_kanala = naziv_kanala;
    }

    protected Meme(Parcel in) {
        id = in.readString();
        id_kanala = in.readInt();
        naziv_kanala = in.readString();
    }

    public static final Creator<Meme> CREATOR = new Creator<Meme>() {
        @Override
        public Meme createFromParcel(Parcel in) {
            return new Meme(in);
        }

        @Override
        public Meme[] newArray(int size) {
            return new Meme[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(id_kanala);
        dest.writeString(naziv_kanala);
    }
}

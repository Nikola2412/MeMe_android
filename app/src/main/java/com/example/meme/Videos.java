package com.example.meme;

import android.view.View;
import android.widget.Toast;

public class Videos {

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
}

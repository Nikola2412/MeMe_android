package com.example.meme;

import android.view.View;
import android.widget.Toast;

public class Videos {

    public String naziv;
    public String thubnail;

    public String link;

    public Videos(String naziv, String thubnail, String link) {
        this.naziv = naziv;
        this.thubnail = thubnail;
        this.link = link;
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
}

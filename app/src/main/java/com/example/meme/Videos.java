package com.example.meme;

import android.view.View;
import android.widget.Toast;

public class Videos {

    public String naziv;
    public int thubnail;

    String link;

    public Videos(String naziv, int thubnail, String link) {
        this.naziv = naziv;
        this.thubnail = thubnail;
        this.link = link;
    }
}

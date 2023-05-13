package com.example.meme;

public class Meme {
    public String id;
    public int id_kanala;

    public Meme(String id, int id_kanala) {
        this.id = id;
        this.id_kanala = id_kanala;
    }

    public String getId() {
        return id;
    }

    public int getId_kanala() {
        return id_kanala;
    }
}

package com.tacademy.woosuk_melonapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Tacademy on 2016-11-09.
 */

public class Genres {
    @SerializedName("genre")
    @Expose
    private ArrayList<Genre> genre = new ArrayList<Genre>();

    public ArrayList<Genre> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<Genre> genre) {
        this.genre = genre;
    }
}


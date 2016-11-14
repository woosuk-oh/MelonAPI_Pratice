package com.tacademy.woosuk_melonapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tacademy on 2016-11-09.
 */

public  class GenreList {

    @SerializedName("genres")
    @Expose
    private Genres genres;

    public Genres getGenres() {
        return genres;
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }
}

package com.tacademy.woosuk_melonapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tacademy on 2016-11-09.
 */

public  class Genre {

    @SerializedName("genreName")
    @Expose
    private String genreName;

    @SerializedName("genreId")
    @Expose
    private String genreId;




    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}

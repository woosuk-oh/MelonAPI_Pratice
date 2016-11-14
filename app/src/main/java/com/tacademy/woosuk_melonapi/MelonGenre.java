package com.tacademy.woosuk_melonapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tacademy on 2016-11-09.
 */

public class MelonGenre {

    @SerializedName("melon")
    @Expose
    private GenreList melon;

    public GenreList getMelon() {
        return melon;
    }

    public void setMelon(GenreList melon) {
        this.melon = melon;
    }
}

package com.tacademy.woosuk_melonapi;

import org.json.JSONObject;

/**
 * Created by Tacademy on 2016-11-08.
 */

public class Melon implements JSONParseHandler{
    int menuId;
    int count;
    int page;
    int totalPages;
    Songs songs;

    @Override
    public void setData(JSONObject jobject) {
        menuId = jobject.optInt("menuId");
        count = jobject.optInt("count");
        page = jobject.optInt("page");
        totalPages = jobject.optInt("totalPages");
        JSONObject jsongs = jobject.optJSONObject("song");
        songs = new Songs();
        songs.setData(jsongs);
    }
}

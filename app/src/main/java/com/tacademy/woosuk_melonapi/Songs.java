package com.tacademy.woosuk_melonapi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tacademy on 2016-11-08.
 */

public class Songs implements JSONParseHandler{
    @com.google.gson.annotations.SerializedName("song")
    ArrayList<Song> songlist;

    @Override
    public void setData(JSONObject jobject) {
        JSONArray jsong = jobject.optJSONArray("songlist");
        songlist = new ArrayList<Song>();

        for(int i=0; i<jsong.length(); i++){
            JSONObject js = jsong.optJSONObject(i);
            Song s = new Song();
            s.setData(js);
            songlist.add(s);
        }
    }
}

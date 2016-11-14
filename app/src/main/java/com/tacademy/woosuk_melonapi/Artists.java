package com.tacademy.woosuk_melonapi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tacademy on 2016-11-08.
 */

public class Artists implements JSONParseHandler {
    ArrayList<Artist> artist;

    @Override
    public void setData(JSONObject jobject) {
        JSONArray jarray = jobject.optJSONArray("artist");
        artist = new ArrayList<Artist>();
        for(int i = 0; i< jarray.length(); i++){
            JSONObject jitem = jarray.optJSONObject(i);
            Artist art = new Artist();
            artist.add(art);
        }
    }
}

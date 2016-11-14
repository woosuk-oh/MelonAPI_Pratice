package com.tacademy.woosuk_melonapi;

import org.json.JSONObject;

/**
 * Created by Tacademy on 2016-11-08.
 */

public class Song implements JSONParseHandler {
    int songId;
    String songName;
    Artists artists;
    String albumName;
    int currentRank;

    @Override
    public void setData(JSONObject jobject) {
        songId = jobject.optInt("songId");
        songName = jobject.optString("songName");
        currentRank = jobject.optInt("currentRank");
        JSONObject jartist = jobject.optJSONObject("artists");
        artists = new Artists();
        artists.setData(jartist);
    }

    @Override
    public String toString() {
        return "[" + currentRank + "]" + songName + "\n("+albumName+")";
    }
}

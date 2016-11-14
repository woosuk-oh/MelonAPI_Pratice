package com.tacademy.woosuk_melonapi;

import org.json.JSONObject;

/**
 * Created by Tacademy on 2016-11-08.
 */

public class Artist implements JSONParseHandler {
    int artistId;
    String artistName;

    @Override
    public void setData(JSONObject jobject) {
        artistId = jobject.optInt("artistId");
        artistName = jobject.optString("artistName");
    }
}

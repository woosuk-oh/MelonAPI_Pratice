package com.tacademy.woosuk_melonapi;

import org.json.JSONObject;

/**
 * Created by Tacademy on 2016-11-08.
 */

public class MelonData implements JSONParseHandler {

    Melon melon;

    @Override
    public void setData(JSONObject jobject) {
        JSONObject jmelon = jobject.optJSONObject("melon");
        melon = new Melon();
        melon.setData(jmelon);
    }
}

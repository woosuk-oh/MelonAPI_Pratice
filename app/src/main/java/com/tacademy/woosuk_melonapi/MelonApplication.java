package com.tacademy.woosuk_melonapi;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tacademy on 2016-11-08.
 */

public class MelonApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate(){
        super.onCreate();
        mContext = this;
    }
    public static Context getMelonContext(){
        return mContext;
    }
}

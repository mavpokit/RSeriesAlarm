package com.mavpokit.rseriesalarm.util;

import android.util.Log;

import com.mavpokit.rseriesalarm.BuildConfig;

/**
 * Created by Alex on 26.01.2017.
 */

public class Logger {
    public static void log(String tag, String msg){
        if (BuildConfig.DEBUG)
            Log.d(tag,msg);
    }
}

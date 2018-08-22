package com.superw.androidproject.common;

import android.app.Application;

/**
 * Created by wangyanchao on 2017/2/16.
 */

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }
}

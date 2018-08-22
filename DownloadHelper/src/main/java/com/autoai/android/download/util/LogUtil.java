package com.autoai.android.download.util;

import android.util.Log;

/**
 * Created by wangyanchao on 2018/7/10.
 */

public class LogUtil {

    public static void e(String tag, String message) {
        Log.e(tag, message);
    }

    public static void e(String tag, String message, Throwable e) {
        Log.e(tag, message, e);
    }
}

package com.autoai.android.toolkit.utils;

import android.os.Looper;

/**
 * Created by wangyanchao on 2018/9/4.
 */

public class ThreadUtils {

    public static boolean isMain() {
        return Looper.getMainLooper().isCurrentThread();
    }

}

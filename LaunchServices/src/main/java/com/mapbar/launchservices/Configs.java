package com.mapbar.launchservices;

/**
 * Created by wangyanchao on 27/09/2017.
 */

public class Configs {

    public static final int NORMAL_RELEASE_CHANNEL = 0;

    public static final int WELINK_RELEASE_CHANNEL = 1;

    public static final int JF_RELEASE_CHANNEL = 2;

    /* old */
//    public static final int DEFAULT_RELEASE_CHANNEL = NORMAL_RELEASE_CHANNEL;
//    public static final int DEFAULT_RELEASE_CHANNEL = WELINK_RELEASE_CHANNEL;
    public static final int DEFAULT_RELEASE_CHANNEL = JF_RELEASE_CHANNEL;

    public static final String LAUNCH_WELINK_SERVICE_ACTION = "com.mapbar.android.LAUNCH_WELINK_SERVICE_ACTION";
    public static final String LAUNCH_JF_SERVICE_ACTION = "com.mapbar.android.LAUNCH_JF_SERVICE_ACTION";

    public static boolean isWelink() {
        return DEFAULT_RELEASE_CHANNEL == WELINK_RELEASE_CHANNEL;
    }

    public static boolean isJF() {
        return DEFAULT_RELEASE_CHANNEL == JF_RELEASE_CHANNEL;
    }

    /* new */
    public static final String LAUNCH_SERVICE_ACTION = "com.mapbar.android.LAUNCH_SERVICE";
    public static final String REPLY_SERVICE_ACTION = "com.mapbar.android.REPLY_SERVICE";

    public static final int WELINK_PRIORITY = 300;
    public static final int JF_PRIORITY = 200;
    public static final int NORMAL_PRIORITY = 100;

    public static final int DEFAULT_PRIORITY = JF_PRIORITY;

    public static final String BROADCAST_PRIORITY_KEY = "priority";
    public static final String BROADCAST_PACKAGE_NAME_KEY = "packageName";
}

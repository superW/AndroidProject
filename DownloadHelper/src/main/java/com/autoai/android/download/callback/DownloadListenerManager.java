package com.autoai.android.download.callback;

import android.os.SystemClock;

import com.autoai.android.download.util.LogUtil;

import java.util.Hashtable;

/**
 * Created by wangyanchao on 2018/8/2.
 */

public class DownloadListenerManager {

    private static final String TAG = "DownloadListenerManager";

    private volatile static DownloadListenerManager SINGLETANCE;

    private Hashtable<String, DownloadListener> mCallBacks;

    private DownloadListenerManager() {
        LogUtil.e(TAG, "Constructor");
        this.mCallBacks = new Hashtable<String, DownloadListener>();
    }

    public static DownloadListenerManager getInstance() {
        if (SINGLETANCE == null) {
            LogUtil.e(TAG, "getInstance");
            synchronized (DownloadListenerManager.class) {
                if (SINGLETANCE == null) {
                    SINGLETANCE = new DownloadListenerManager();
                }
            }
        }
        return SINGLETANCE;
    }

    public synchronized String addDownloadListener(DownloadListener listener) {
        if (mCallBacks != null) {
            String key = String.valueOf(SystemClock.currentThreadTimeMillis());
            mCallBacks.put(key, listener);
            return key;
        }
        return null;
    }

    public void sendCall(String action, Object obj) {
        if (mCallBacks != null && mCallBacks.size() > 0) {
            for (String key : mCallBacks.keySet()) {
                DownloadListener listener = mCallBacks.get(key);
                if (listener != null) {
                    listener.onDownloadResult(action, obj);
                }
            }
        }
    }



}

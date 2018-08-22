package com.autoai.android.demos.net_state_modify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by wangyanchao on 2018/8/12.
 */

public class NetStateModifyReceiver extends BroadcastReceiver {

    private static final String TAG = "NetStateModifyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e(TAG, "action=" + action);
    }
}

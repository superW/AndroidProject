package com.mapbar.launchservices;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by wangyanchao on 27/09/2017.
 */

public class LaunchService extends Service {

    private static final String TAG = "LaunchService";

    private LaunchReceiver receiver;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "---Service onCreate---");

//        intentStickyBroadcast();

        intentBroadcast();
    }

    /**
     * 通过普通广播实现
     */
    private void intentBroadcast() {

        receiver = new LaunchReceiver();
        receiver.setCommandListener(new LaunchReceiver.CommandListener() {
            @Override
            public void onStopCommand() {
                stopSelf();
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(Configs.LAUNCH_SERVICE_ACTION);
        filter.addAction(Configs.REPLY_SERVICE_ACTION);
        registerReceiver(receiver, filter);

        // launch service broadcast
        Intent intent = new Intent();
        intent.setAction(Configs.LAUNCH_SERVICE_ACTION);
        intent.putExtra(Configs.BROADCAST_PRIORITY_KEY, Configs.DEFAULT_PRIORITY);
        String packageName = getPackageName();
        if (!TextUtils.isEmpty(packageName)) {
            intent.putExtra(Configs.BROADCAST_PACKAGE_NAME_KEY, packageName);
        }
        sendBroadcast(intent);

    }

    /**
     * 通过粘性广播实现
     */
    private void intentStickyBroadcast() {
        if (Configs.isWelink()) {
            // welink
            Intent intent = new Intent();
            intent.setAction(Configs.LAUNCH_WELINK_SERVICE_ACTION);
            sendStickyBroadcast(intent);
        } else if (Configs.isJF()) {
            // JF
            receiver = new LaunchReceiver();
            receiver.setLaunchListener(new LaunchReceiver.LaunchListener() {
                @Override
                public void onLaunch(String action) {
                    stopSelf();
                }
            });
            IntentFilter filter = new IntentFilter();
            filter.addAction(Configs.LAUNCH_WELINK_SERVICE_ACTION);
            registerReceiver(receiver, filter);

            Intent intent = new Intent();
            intent.setAction(Configs.LAUNCH_JF_SERVICE_ACTION);
            sendStickyBroadcast(intent);
        } else {
            // app
            receiver = new LaunchReceiver();
            receiver.setLaunchListener(new LaunchReceiver.LaunchListener() {
                @Override
                public void onLaunch(String action) {
                    stopSelf();
                }
            });
            IntentFilter filter = new IntentFilter();
            filter.addAction(Configs.LAUNCH_WELINK_SERVICE_ACTION);
            filter.addAction(Configs.LAUNCH_JF_SERVICE_ACTION);
            registerReceiver(receiver, filter);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "---Service onDestroy---");

        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}

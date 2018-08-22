package com.mapbar.launchservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by wangyanchao on 27/09/2017.
 */

public class LaunchReceiver extends BroadcastReceiver {

    private static final String TAG = "LaunchReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            // 接收广播
            // 如果收到welink发来的广播，停止启动服务，或者通过dispatch的stop方法
            String action = intent.getAction();
            Log.e(TAG, "onReceive action = " + intent.getAction());

//            if (!TextUtils.isEmpty(action) && this.launchListener != null) {
//                this.launchListener.onLaunch(action);
//            }

            if (!TextUtils.isEmpty(action)) {
                String packageNameExtra = intent.getStringExtra(Configs.BROADCAST_PACKAGE_NAME_KEY);
                Log.e(TAG, "packageNameExtra = " + packageNameExtra);

                if (Configs.LAUNCH_SERVICE_ACTION.equals(action)) {
                    int priority = intent.getIntExtra(Configs.BROADCAST_PRIORITY_KEY, -1);
                    if (priority == -1) return;

                    if (priority > Configs.DEFAULT_PRIORITY) {
                        if (commandListener != null) {
                            commandListener.onStopCommand();
                        }
                    } else if (priority < Configs.DEFAULT_PRIORITY) {
                        Intent intentReply = new Intent();
                        intentReply.setAction(Configs.REPLY_SERVICE_ACTION);
                        intentReply.putExtra(Configs.BROADCAST_PRIORITY_KEY, Configs.DEFAULT_PRIORITY);
                        String packageName = context.getPackageName();
                        if (!TextUtils.isEmpty(packageName)) {
                            intentReply.putExtra(Configs.BROADCAST_PACKAGE_NAME_KEY, packageName);
                        }
                        context.sendBroadcast(intentReply);
                    }
                } else if (Configs.REPLY_SERVICE_ACTION.equals(action)) {
                    int replyPriority = intent.getIntExtra(Configs.BROADCAST_PRIORITY_KEY, -1);
                    if (replyPriority == -1) return;

                    if (replyPriority > Configs.DEFAULT_PRIORITY) {
                        if (commandListener != null) {
                            commandListener.onStopCommand();
                        }
                    }
                }

            }

        }
    }

    private LaunchListener launchListener;

    public void setLaunchListener(LaunchListener launchListener) {
        this.launchListener = launchListener;
    }

    public interface LaunchListener {
        void onLaunch(String action);
    }

    private CommandListener commandListener;

    public void setCommandListener(CommandListener commandListener) {
        this.commandListener = commandListener;
    }

    public interface CommandListener {
        void onStopCommand();
    }
}

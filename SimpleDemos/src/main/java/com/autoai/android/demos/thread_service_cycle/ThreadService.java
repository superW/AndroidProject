package com.autoai.android.demos.thread_service_cycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ThreadService extends Service {
    private static final String TAG = "ThreadService";

    public ThreadService() {
        Log.e(TAG, "===construct thread service===");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "----onCreate----");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e(TAG, "The thread in the service is running.");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "----onDestroy----");
    }
}

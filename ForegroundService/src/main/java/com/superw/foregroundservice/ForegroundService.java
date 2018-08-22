package com.superw.foregroundservice;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ForegroundService extends Service {
    private static final String TAG = "ForegroundService";

    public ForegroundService() {
    }

    /**
     * id不可设置为0,否则不能设置为前台service
     */
    private static final int NOTIFICATION_DOWNLOAD_PROGRESS_ID = 0x0001;

    private boolean isRemove=false;//是否需要移除

    /**
     * Notification
     */
    public void createNotification(){
        //使用兼容版本
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        //设置状态栏的通知图标
//        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置通知栏横条的图标
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(true);
        //禁止滑动删除
        builder.setOngoing(false);
        //右上角的时间显示
//        builder.setShowWhen(true);
        //设置通知栏的标题内容
//        builder.setContentTitle("I am Foreground Service!!!");
        //创建通知
        Notification notification = builder.build();
        //设置为前台服务
        startForeground(NOTIFICATION_DOWNLOAD_PROGRESS_ID,notification);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "==onCreate==");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "==onStartCommand==intent=" + intent +
                ", flags=" + flags +
                ", startId=" + startId);

        int i=intent.getExtras().getInt("cmd");
        if(i==0){
            if(!isRemove) {
                createNotification();
            }
            isRemove=true;
        }else {
            //移除前台服务
            if (isRemove) {
                stopForeground(true);
            }
            isRemove=false;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "==onDestroy==");
        //移除前台服务
        if (isRemove) {
            stopForeground(true);
        }
        isRemove=false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

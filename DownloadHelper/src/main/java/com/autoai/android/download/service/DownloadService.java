package com.autoai.android.download.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.autoai.android.download.bean.DownloadInfo;
import com.autoai.android.download.bean.FileInfo;
import com.autoai.android.download.bean.RequestInfo;
import com.autoai.android.download.callback.DownloadListener;
import com.autoai.android.download.callback.DownloadListenerManager;
import com.autoai.android.download.constant.DownloadConstant;
import com.autoai.android.download.constant.DownloadStatus;
import com.autoai.android.download.constant.InnerConstant;
import com.autoai.android.download.db.DbHolder;
import com.autoai.android.download.execute.DownloadExecutor;
import com.autoai.android.download.execute.DownloadTask;
import com.autoai.android.download.util.DebugUtils;
import com.autoai.android.download.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class DownloadService extends Service {

    public static final String TAG = "DownloadService";

    public static boolean canRequest = true;

    // 关于线程池的一些配置
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(3, CPU_COUNT / 2);
    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2;
    private static final long KEEP_ALIVE_TIME = 0L;

    private DownloadExecutor mExecutor = new DownloadExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());

    // 存储任务
    private HashMap<String, DownloadTask> mTasks = new HashMap<>();

    private DbHolder mDbHolder;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e(TAG, "onCreate() -> ");
        mDbHolder = new DbHolder(getBaseContext());
        initDownloadManager();
    }

    @Override
    public void onDestroy() {
        mDbHolder.close();
        LogUtil.e(TAG, "onDestroy() -> ");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (canRequest) {
            LogUtil.e(TAG, "onStartCommand() -> 启动了service服务 intent=" + intent + "\t this=" + this);
            canRequest = false;

            if (null != intent && intent.hasExtra(InnerConstant.Inner.SERVICE_INTENT_EXTRA)) {
                try {
                    ArrayList<RequestInfo> requesetes =
                            (ArrayList<RequestInfo>) intent.getSerializableExtra(InnerConstant.Inner.SERVICE_INTENT_EXTRA);
                    if (null != requesetes && requesetes.size() > 0) {
                        for (RequestInfo request : requesetes) {
                            executeDownload(request);
                        }
                    }
                } catch (Exception e) {
                    LogUtil.e(TAG, "onStartCommand()-> 接受数据,启动线程中发生异常");
                }
            }
            canRequest = true;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private synchronized void executeDownload(RequestInfo requestInfo) {
        DownloadInfo downloadInfo = requestInfo.getDownloadInfo();

        // 先看看在任务列表里，是否有这个任务
        DownloadTask task = mTasks.get(downloadInfo.getUniqueId());
        FileInfo fileInfo = mDbHolder.getFileInfo(downloadInfo.getUniqueId());
        LogUtil.e(TAG, "executeDownload() -> task=" + task + "\t fileInfo=" + fileInfo);

        if (null == task) { // 之前没有类似任务
            if (null != fileInfo) {
                boolean fileExists = downloadInfo.getFile().exists();
                if (fileExists) {
                    switch (fileInfo.getDownloadStatus()) {
                        case DownloadStatus.LOADING:
                        case DownloadStatus.PREPARE:
                            // 修正文件状态
                            mDbHolder.updateState(fileInfo.getId(), DownloadStatus.PAUSE);
                            fileInfo.setDownloadStatus(DownloadStatus.PAUSE);
                            break;
                        case DownloadStatus.COMPLETE:
                            if (!TextUtils.isEmpty(downloadInfo.getAction())) {
                                Intent intent = new Intent();
                                intent.setAction(downloadInfo.getAction());
                                intent.putExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD, fileInfo);
                                sendBroadcast(intent);
                                DownloadListenerManager.getInstance().sendCall(downloadInfo.getAction(), fileInfo);
                            }
                            return;
                    }
                } else { // 文件不存在则删除数据库中数据
                    mDbHolder.deleteFileInfo(downloadInfo.getUniqueId());
                }
            }

            // 创建一个下载任务
            if (requestInfo.getDictate() == InnerConstant.Request.loading) {
                task = new DownloadTask(this, downloadInfo, mDbHolder);
                mTasks.put(downloadInfo.getUniqueId(), task);
            }
        } else {
            // 存在下载任务，文件被意外删除的情况
            if (!downloadInfo.getFile().exists()) {
                task.pause();
                mTasks.remove(downloadInfo.getUniqueId());
                mDbHolder.deleteFileInfo(downloadInfo.getUniqueId());
                LogUtil.e(TAG, " 状态标示" + DebugUtils.getStatusDesc(task.getStatus()) + "，但是文件不存在，重新执行下载文件  ");
                executeDownload(requestInfo);
                return;
            }
        }

        if (null != task) {
            if (requestInfo.getDictate() == InnerConstant.Request.loading) {
                mExecutor.executeTask(task);
            } else {
                task.pause();
            }
        }
    }

    private void initDownloadManager() {
        DownloadListenerManager.getInstance().addDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadResult(String action, Object obj) {
                LogUtil.e(TAG, "action=" + action +
                        ", obj=" + obj.toString());
            }
        });
    }
}

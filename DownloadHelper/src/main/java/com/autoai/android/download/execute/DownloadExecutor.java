package com.autoai.android.download.execute;


import android.content.Intent;

import com.autoai.android.download.callback.DownloadListenerManager;
import com.autoai.android.download.constant.DownloadConstant;
import com.autoai.android.download.constant.DownloadStatus;
import com.autoai.android.download.util.LogUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DownloadExecutor extends ThreadPoolExecutor {

    public static final String TAG = "DownloadExecutor";

    public DownloadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                            TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public void executeTask(DownloadTask task) {
        int status = task.getStatus();
        if (status == DownloadStatus.PAUSE || status == DownloadStatus.FAIL) {
            task.setFileStatus(DownloadStatus.WAIT);

            Intent intent = new Intent();
            intent.setAction(task.getDownLoadInfo().getAction());
            intent.putExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD, task.getFileInfo());
            task.sendBroadcast(intent);
            DownloadListenerManager.getInstance().sendCall(task.getDownLoadInfo().getAction(), task.getFileInfo());

            execute(task);
        } else {
            LogUtil.e(TAG, "文件状态不正确, 不进行下载 FileInfo=" + task.getFileInfo());
        }
    }
}

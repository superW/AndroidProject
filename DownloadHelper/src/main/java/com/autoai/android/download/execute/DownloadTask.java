package com.autoai.android.download.execute;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.autoai.android.download.bean.DownloadInfo;
import com.autoai.android.download.bean.FileInfo;
import com.autoai.android.download.callback.DownloadListenerManager;
import com.autoai.android.download.constant.DownloadConstant;
import com.autoai.android.download.constant.DownloadStatus;
import com.autoai.android.download.db.DbHolder;
import com.autoai.android.download.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask implements Runnable {

    public static final String TAG = "DownloadTask";

    private Context context;
    private DownloadInfo info;
    private FileInfo mFileInfo;
    private DbHolder dbHolder;
    private boolean isPause;

    public DownloadTask(Context context, DownloadInfo info, DbHolder dbHolder) {
        this.context = context;
        this.info = info;
        this.dbHolder = dbHolder;

        FileInfo fileInfoFromDb = dbHolder.getFileInfo(info.getUniqueId());
        String url = info.getUrl();
        String filePath = info.getFile().getAbsolutePath();
        long fileSize = 0;
        long location = 0;
        if (null != fileInfoFromDb) {
            url = fileInfoFromDb.getDownloadUrl();
            filePath = fileInfoFromDb.getFilePath();
            fileSize = fileInfoFromDb.getSize();
            location = fileInfoFromDb.getDownloadLocation();
        }

        //初始化下载文件信息
        mFileInfo = new FileInfo();
        mFileInfo.setId(info.getUniqueId());
        mFileInfo.setDownloadUrl(url);
        mFileInfo.setFilePath(filePath);
        mFileInfo.setSize(fileSize);
        mFileInfo.setDownloadLocation(location);

        LogUtil.e(TAG, "构造函数() -> 初始化完毕  mFileInfo=" + mFileInfo);
    }

    @Override
    public void run() {
        download();
    }

    public void pause() {
        isPause = true;
    }

    public int getStatus() {
        if (null != mFileInfo) {
            return mFileInfo.getDownloadStatus();
        }
        return DownloadStatus.FAIL;
    }

    public void setFileStatus(int status) {
        mFileInfo.setDownloadStatus(status);
    }

    public void sendBroadcast(Intent intent) {
        context.sendBroadcast(intent);
    }

    public DownloadInfo getDownLoadInfo() {
        return info;
    }

    public FileInfo getFileInfo() {
        return mFileInfo;
    }

    private void download() {
        mFileInfo.setDownloadStatus(DownloadStatus.PREPARE);
        LogUtil.e(TAG, "准备开始下载");

        Intent intent = new Intent();
        intent.setAction(info.getAction());
        intent.putExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD, mFileInfo);
        context.sendBroadcast(intent);
        DownloadListenerManager.getInstance().sendCall(info.getAction(), mFileInfo);

        LogUtil.e(TAG, "download location=" + mFileInfo.getDownloadLocation() +  " fileInfo=" + dbHolder.getFileInfo(info.getUniqueId()));
        if ((mFileInfo.getDownloadLocation() == 0 || dbHolder.getFileInfo(info.getUniqueId()) == null)
                && info.getFile().exists()) {
            info.getFile().delete();
            LogUtil.e(TAG, "download location=0 or db don't contain fileInfo, so file delete.");
        }
        if (dbHolder.getFileInfo(info.getUniqueId()) != null && !info.getFile().exists()) {
            LogUtil.e(TAG, "file = " + info.getFile() + "\n数据库记录表明我们下载过该文件, 但是现在该文件不存在,所以从头开始");
            dbHolder.deleteFileInfo(info.getUniqueId());
        }

        RandomAccessFile accessFile = null;
        HttpURLConnection http = null;
        InputStream inStream = null;

        try {
            URL sizeUrl = new URL(info.getUrl());
            HttpURLConnection sizeHttp = (HttpURLConnection) sizeUrl.openConnection();
            sizeHttp.setRequestMethod("GET");
            sizeHttp.connect();
            long totalSize = sizeHttp.getContentLength();
            sizeHttp.disconnect();

            LogUtil.e(TAG, "download file total size=" + totalSize);

            if (totalSize <= 0) {
                if (info.getFile().exists()) {
                    info.getFile().delete();
                }
                dbHolder.deleteFileInfo(info.getUniqueId());
                LogUtil.e(TAG, "文件大小 = " + totalSize + "\t, 终止下载过程");
                return;
            }

            mFileInfo.setSize(totalSize);
            accessFile = new RandomAccessFile(info.getFile(), "rwd");

            URL url = new URL(info.getUrl());
            http = (HttpURLConnection) url.openConnection();
            http.setConnectTimeout(10000);
            http.setRequestProperty("Connection", "Keep-Alive");
            http.setReadTimeout(10000);
            http.setRequestProperty("Range", "bytes=" + mFileInfo.getDownloadLocation() + "-");
            http.connect();

            LogUtil.e(TAG, "prepare get input stream " + inStream);

            inStream = http.getInputStream();
            byte[] buffer = new byte[1024 * 8];
            int offset;

            LogUtil.e(TAG, "after get input stream " + inStream);

            accessFile.seek(mFileInfo.getDownloadLocation());
            long millis = SystemClock.uptimeMillis();
            LogUtil.e(TAG, "first time="+millis);
            while ((offset = inStream.read(buffer)) != -1) {
                if (isPause) {
                    LogUtil.e(TAG, "下载过程 设置了 暂停");
                    isPause = false;
                    mFileInfo.setDownloadStatus(DownloadStatus.PAUSE);
                    dbHolder.saveFile(mFileInfo);
                    context.sendBroadcast(intent);

                    http.disconnect();
                    accessFile.close();
                    inStream.close();
                    return;
                }
                // 文件在下载中被删除，直接退出下载
                if (!getDownLoadInfo().getFile().exists()) {
                    LogUtil.e(TAG, "下载过程 文件被删除 退出下载");
                    isPause = false;
                    dbHolder.deleteFileInfo(info.getUniqueId());
                    mFileInfo.setDownloadStatus(DownloadStatus.FAIL);
                    context.sendBroadcast(intent);

                    http.disconnect();
                    accessFile.close();
                    inStream.close();
                    return;
                }
                accessFile.write(buffer, 0, offset);
                mFileInfo.setDownloadLocation(mFileInfo.getDownloadLocation() + offset);
                mFileInfo.setDownloadStatus(DownloadStatus.LOADING);

                if (SystemClock.uptimeMillis() - millis >= 1000) {
                    millis = SystemClock.uptimeMillis();
                    dbHolder.saveFile(mFileInfo);
                    context.sendBroadcast(intent);
                    DownloadListenerManager.getInstance().sendCall(info.getAction(), mFileInfo);
                }
            }// end of "while(..."

            mFileInfo.setDownloadStatus(DownloadStatus.COMPLETE);
            dbHolder.saveFile(mFileInfo);
            context.sendBroadcast(intent);
            DownloadListenerManager.getInstance().sendCall(info.getAction(), mFileInfo);
        } catch (Exception e) {
            LogUtil.e(TAG, "下载过程发生失败");
            dbHolder.saveFile(mFileInfo);
            context.sendBroadcast(intent);
        } finally {
            try {
                LogUtil.e(TAG, "finally 块 执行");
                if (accessFile != null) {
                    accessFile.close();
                }
                if (inStream != null) {
                    inStream.close();
                }
                if (http != null) {
                    http.disconnect();
                }
                LogUtil.e(TAG, "finally 块 完成");
            } catch (IOException e) {
                LogUtil.e(TAG, "finally 块 关闭文件过程中 发生异常");
            }
        }
    }
}

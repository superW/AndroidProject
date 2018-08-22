package com.autoai.android.download;

import android.content.Context;
import android.content.Intent;

import com.autoai.android.download.bean.DownloadInfo;
import com.autoai.android.download.bean.RequestInfo;
import com.autoai.android.download.constant.InnerConstant;
import com.autoai.android.download.service.DownloadService;
import com.autoai.android.download.util.LogUtil;

import java.io.File;
import java.util.ArrayList;

public class DownloadHelper {

    public static final String TAG = "DownloadHelper";

    private volatile static DownloadHelper SINGLETANCE;

    private static ArrayList<RequestInfo> requests = new ArrayList<>();

    private DownloadHelper() {
    }

    public static DownloadHelper getInstance() {
        if (SINGLETANCE == null) {
            synchronized (DownloadHelper.class) {
                if (SINGLETANCE == null) {
                    SINGLETANCE = new DownloadHelper();
                }
            }
        }
        return SINGLETANCE;
    }

    /**
     * 提交  下载/暂停  等任务.(提交就意味着开始执行生效)
     *
     * @param context
     */
    public synchronized void submit(Context context) {
        if (requests.isEmpty()) {
            LogUtil.e(TAG, "没有下载任务可供执行");
            return;
        }
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(InnerConstant.Inner.SERVICE_INTENT_EXTRA, requests);
        context.startService(intent);
        requests.clear();
    }

    /**
     * 添加 新的下载任务
     *
     * @param url    下载的url
     * @param file   存储在某个位置上的文件
     * @param action 下载过程会发出广播信息.该参数是广播的action
     * @return DownloadHelper自身 (方便链式调用)
     */
    public DownloadHelper addTask(String url, File file, String action) {
        RequestInfo requestInfo = createRequest(url, file, action, InnerConstant.Request.loading);
        LogUtil.e(TAG, "addTask() requestInfo=" + requestInfo);

        requests.add(requestInfo);
        return this;
    }

    /**
     * 暂停某个下载任务
     *
     * @param url    下载的url
     * @param file   存储在某个位置上的文件
     * @param action 下载过程会发出广播信息.该参数是广播的action
     * @return DownloadHelper自身 (方便链式调用)
     */
    public DownloadHelper pauseTask(String url, File file, String action) {
        RequestInfo requestInfo = createRequest(url, file, action, InnerConstant.Request.pause);
        LogUtil.e(TAG, "pauseTask() -> requestInfo=" + requestInfo);
        requests.add(requestInfo);
        return this;
    }

    private RequestInfo createRequest(String url, File file, String action, int dictate) {
        RequestInfo request = new RequestInfo();
        request.setDictate(dictate);
        request.setDownloadInfo(new DownloadInfo(url, file, action));
        return request;
    }
}

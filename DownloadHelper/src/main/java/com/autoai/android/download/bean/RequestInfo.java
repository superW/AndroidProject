package com.autoai.android.download.bean;

import com.autoai.android.download.util.DebugUtils;

import java.io.Serializable;

public class RequestInfo implements Serializable {

    //下载的控制状态
    private int dictate;

    private DownloadInfo downloadInfo;

    public RequestInfo() {
    }

    public int getDictate() {
        return dictate;
    }

    public void setDictate(int dictate) {
        this.dictate = dictate;
    }

    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public void setDownloadInfo(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "dictate=" + DebugUtils.getRequestDictateDesc(dictate) +
                ", downloadInfo=" + downloadInfo +
                '}';
    }
}

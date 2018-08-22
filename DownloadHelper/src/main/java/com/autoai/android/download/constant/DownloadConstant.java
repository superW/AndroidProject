package com.autoai.android.download.constant;

public class DownloadConstant {

    /**
     * 下载过程会通过发送广播, 广播通过intent携带文件数据的 信息。
     * intent 的key值就是该字段
     * eg : FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD);
     */
    public static final String EXTRA_INTENT_DOWNLOAD = "ata_dl_extra";
}

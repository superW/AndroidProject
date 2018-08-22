package com.autoai.android.download.constant;

public class InnerConstant {

    /* DB 数据库中用到的字段 */
    public static class Db {
        public static final String id = "id";
        public static final String downloadUrl = "downloadUrl";
        public static final String filePath = "filePath";
        public static final String size = "size";
        public static final String downloadLocation = "downloadLocation";
        public static final String downloadStatus = "downloadStatus";

        public static final String NAME_TABALE = "download_info";
        public static final String NAME_DB = "download.Db";
    }

    /* 请求参数中用到的信息 */
    public static class Request {
        // 下载状态
        public static final int loading = 10;
        // 暂停状态
        public static final int pause = 11;
    }

    public static class Inner {
        public static final String SERVICE_INTENT_EXTRA = "service_intent_extra";
    }
}

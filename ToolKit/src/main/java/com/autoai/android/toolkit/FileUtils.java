package com.autoai.android.toolkit;

import java.io.File;

/**
 * Created by wangyanchao on 2018/7/17.
 */

public class FileUtils {

    public static long getFolderSize(File file) {
        long total = 0L;
        if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            if (fileList != null && fileList.length > 0) {
                for (File f : fileList) {
                    if (f.isDirectory()) {
                        total += getFolderSize(f);
                    } else {
                        total += f.length();
                    }
                }
            }
        } else {
            total = file.length();
        }
        return total;
    }

    public static long getFileSize(File file) {
        return file.length();
    }

}

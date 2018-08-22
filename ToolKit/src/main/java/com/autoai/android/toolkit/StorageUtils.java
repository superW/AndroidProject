package com.autoai.android.toolkit;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.util.Log;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyanchao on 2018/8/3.
 */

public class StorageUtils {

    private static final String TAG = "StorageUtils";

    //    public static final String STORAGE_PATH = "/mnt/sdcard/mapbar/";
    public static final String STORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
//    public static final String STORAGE_PATH = "/data/data/";

    public static long getTotalSpace(String path) {
        long total = 0L;
        StatFs statFs = new StatFs(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            total = statFs.getTotalBytes();
        } else {
            BigDecimal bcount = new BigDecimal(statFs.getBlockCount());
            BigDecimal bSize = new BigDecimal(statFs.getBlockSize());
            total = bcount.multiply(bSize).longValue();
        }
        return total;
    }

    public static long getUsedSpace(String path) {
        long used = 0L;
        StatFs statFs = new StatFs(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            used = statFs.getTotalBytes() - statFs.getAvailableBytes();
        } else {
            BigDecimal bcount = new BigDecimal(statFs.getBlockCount() - statFs.getAvailableBlocks());
            BigDecimal bSize = new BigDecimal(statFs.getBlockSize());
            used = bcount.multiply(bSize).longValue();
        }
        return used;
    }

    public static long getAvailableSpace(String path) {
        long available = 0L;
        StatFs statFs = new StatFs(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            available = statFs.getAvailableBytes();
        } else {
            BigDecimal bcount = new BigDecimal(statFs.getAvailableBlocks());
            BigDecimal bSize = new BigDecimal(statFs.getBlockSize());
            available = bcount.multiply(bSize).longValue();
        }
        return available;
    }

    public static List<StorageInfo> listAvaliable(Context context) {
        ArrayList<StorageInfo> storagges = new ArrayList<StorageInfo>();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList", paramClasses);
            getVolumeList.setAccessible(true);
            Object[] params = {};
            Object[] invokes = (Object[]) getVolumeList.invoke(storageManager, params);
            if (invokes != null) {
                StorageInfo info = null;
                for (int i = 0; i < invokes.length; i++) {
                    Object obj = invokes[i];
                    Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
                    String path = (String) getPath.invoke(obj, new Object[0]);
                    info = new StorageInfo(path);
                    File file = new File(info.path);
                    Log.e(TAG, "path=" + path + ", state=" +
                            StorageManager.class.getMethod("getVolumeState", String.class).invoke(storageManager, info.path) +
                            ", isMounted=" + info.isMounted());
                    if ((file.exists()) && (file.isDirectory()) && (file.canWrite())) {
                        Method isRemovable = obj.getClass().getMethod("isRemovable", new Class[0]);
                        String state = null;
                        try {
                            Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
                            state = (String) getVolumeState.invoke(storageManager, info.path);
                            info.state = state;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (info.isMounted()) {
                            info.isRemoveable = ((Boolean) isRemovable.invoke(obj, new Object[0])).booleanValue();
                            storagges.add(info);
                        }
                    }
                }
            }
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        storagges.trimToSize();

        return storagges;
    }


    /**
     * 指定路径下的所有文件夹的可读写状态（打印日志）
     *
     * @param path  指定的路径
     * @param level 路径下需要遍历的层级数(例:路径下的所有三级以内的目录结构,level=3)
     */
    public static void iterableFile(String path, int level) {
        iterableFile(path, 0, level);
    }

    private static void iterableFile(String path, int i, int level) {
        if (i >= level) {
            return;
        } else {
            i++;
        }
        File file = new File(path);

        if (LogManager.isLoggable()) {
            LogManager.e("iterable", "directory:" + path +
                    " canRead=" + file.canRead() +
                    " canWrite(system api)=" + file.canWrite() +
                    " canWrite(custom api)=" + canWrite(file));
        }

        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    if (f.exists() && f.isDirectory()) {
                        String dirPath = f.getAbsolutePath();
                        iterableFile(dirPath, i, level);
                    }
                }
            }
        }
    }

    /**
     * 文件夹是否可写文件
     * 写入文件后删除
     *
     * @param file
     * @return
     */
    private static boolean canWrite(File file) {
        boolean can = false;
        try {
            if (file != null && file.exists() && file.isDirectory()) {
                File f = new File(file, "temp" + System.currentTimeMillis() + ".txt");
                if (LogManager.isLoggable()) {
                    LogManager.e("WRITE", "PATH=" + f.getAbsolutePath());
                }
                can = f.createNewFile();
                if (can) {
                    f.delete();
                    return can;
                }
            }
        } catch (Exception e) {
            if (LogManager.isLoggable()) {
                LogManager.e("WRITE", "canw err", e);
            }
        }
        return can;
    }


    public static class StorageInfo {
        public String path;
        public String state;
        public boolean isRemoveable;

        public StorageInfo(String path) {
            this.path = path;
        }

        public boolean isMounted() {
            return "mounted".equals(state);
        }

        @Override
        public String toString() {
            return "StorageInfo{" +
                    "path='" + path + '\'' +
                    ", state='" + state + '\'' +
                    ", isRemoveable=" + isRemoveable +
                    '}';
        }
    }

}

package com.autoai.android.demos.disk_lru_cache;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yunzhanjun on 2017/12/16.
 */

public class DiskCacheController {

    private static final String TAG = "testpro";

    private boolean isRunning = false;

    private Context mContext;
    private ExecutorService executorService;

    public DiskCacheController(Context context){
        mContext = context;
    }

    public void start(){
        if(isRunning){
            return;
        }
        isRunning = true;
        executorService = Executors.newFixedThreadPool(10);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                initCache(mContext);
            }
        });
    }

    public void stop(){
        if(!isRunning){
            return;
        }
        isRunning = false;
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                closeCacheDir();
            }
        });
        executorService.shutdown();
        executorService = null;
    }

    public void saveBitmap2File(final byte[] data, final int size, final String filename){
        if(!isRunning){
            return;
        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                saveFile(data, size, filename);
            }
        });
    }

    //使用DiskLruCache存储数据

    private DiskLruCache mDiskLruCache;
    private void initCache(Context context){
        try {
            File cacheDir = getDiskCacheDir(context, "/mnt/sdcard/mapbar/media");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, 10, 1, 1024 * 1024 * 1024);
//            mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1, 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            cachePath = context.getFilesDir().getAbsolutePath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private void closeCacheDir(){
        if(mDiskLruCache != null && !mDiskLruCache.isClosed()){
            try {
                mDiskLruCache.close();
                mDiskLruCache = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void saveFile(byte[] data, int size, String filename){

        try {
//            byte[] buffer = new byte[size];
//            System.arraycopy(data,0,buffer,0,size);
//            byte[] gzipBuffer = DataUtils.compressForGzip(buffer);
//
//            DiskLruCache.Editor editor = mDiskLruCache.edit(filename);
//            OutputStream os = editor.newOutputStream(0);
//            BufferedOutputStream bos = new BufferedOutputStream(os,gzipBuffer.length);
//            bos.write(gzipBuffer,0,gzipBuffer.length);

            DiskLruCache.Editor editor = mDiskLruCache.edit(filename);
            OutputStream os = editor.newOutputStream(0);
            BufferedOutputStream bos = new BufferedOutputStream(os,data.length);
            bos.write(data,0,data.length);

//            BufferedOutputStream bos = new BufferedOutputStream(os,buffer.length);
//            bos.write(data,0,buffer.length);
//            double len = buffer.length;
//            LogManager.e("share", "gzip size: " + len);
//            double bi = (len/size) * 100;
//            LogManager.e("share", "gzip size/readMjpeg size: " + bi);
            bos.close();
            editor.commit();
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

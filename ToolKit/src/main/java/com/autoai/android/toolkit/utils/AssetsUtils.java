package com.autoai.android.toolkit.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetsUtils {

    public interface Loadlistener {
        void onStart();

        void onProgress(int progress);

        void onFail(String err);

        void onSuccess(String destfile);
    }

    /**
     * 从asset目录下将指定的文件写到指定位置
     *
     * @param context
     * @param srcfile
     * @param destfile
     * @param mListener
     */
    public static void initDexFile(Context context, String srcfile, String destfile,
                                   Loadlistener mListener) {

        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(srcfile);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(destfile));

            byte[] buf = new byte[1024];
            int ch = -1;
            int total = -1;
            while ((ch = inputStream.read(buf)) != -1) {

                total += ch; // total = total + len1
                if (mListener != null) {
                    mListener.onProgress((int) ((total * 100) / 10000));
                }
                fileOutputStream.write(buf, 0, ch);
            }

            inputStream.close();
            fileOutputStream.close();

            if (mListener != null) {
                mListener.onSuccess(destfile);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            if (mListener != null) {
                mListener.onFail(e.getMessage());
            }
        }


    }


}

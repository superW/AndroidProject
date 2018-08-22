package com.autoai.android.demos.load_dex_jar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoai.android.demos.R;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class LoadDexJarActivity extends Activity {

    private static final String TAG = "LoadDexJarActivity";

    private TextView textView;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_dex_jar);

        textView = (TextView) findViewById(R.id.textView);

        findViewById(R.id.putinBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putFileToLocal();
            }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                try {
//                    InputStream in = MainActivity.class.getResourceAsStream("text.txt");
//                    String result = StreamTool.streamToString(in);
//                    Log.e(TAG, "result=" + result);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                loadPlugin(LoadDexJarActivity.this, getFilesDir().getAbsolutePath(), "loadDex.jar", "com.mapbar.load.LoadTest");
            }
        });

    }


    /**
     * 加载插件并运行
     */
    private void loadPlugin(Context context, String filePath, String fileName, String className) {
        DexClassLoader dexClassLoader = null;
        try {
            String filepath = filePath;
            String dexFileName = fileName;
            dexClassLoader = new DexClassLoader(filepath + File.separator + dexFileName, filepath, null, context.getClassLoader());
            Class clz = dexClassLoader.loadClass(className);
            Log.e(TAG, clz == null ? "class is null." : "class is not null.");
            Log.e(TAG, "loadPlugin  filepath :" + filepath + File.separator + dexFileName);

            // 以下日志是INFO级别
            Method method = clz.getMethod("main", new Class[]{String[].class});
            System.out.println(method == null ? "method is null" : "method is not null");
            method.invoke(clz.newInstance(), new Object[]{new String[]{"haha"}});
            System.out.println("finish");
        } catch (Exception e) {
            Log.e(TAG, "---Exception---" + e.getMessage());
        }
    }


    private void putFileToLocal() {
        AssetsUtils.initDexFile(getApplicationContext(), "loadDex.jar", getFilesDir().getAbsolutePath() + "/loadDex.jar", new AssetsUtils.Loadlistener() {
            @Override
            public void onStart() {
                Log.e(TAG, "---onStart---");
            }

            @Override
            public void onProgress(int progress) {
                Log.e(TAG, "---onProgress---");
            }

            @Override
            public void onFail(String err) {
                Log.e(TAG, "---onFail---" + err);
            }

            @Override
            public void onSuccess(String destfile) {
                Log.e(TAG, "---onSuccess---");
            }
        });
    }

}

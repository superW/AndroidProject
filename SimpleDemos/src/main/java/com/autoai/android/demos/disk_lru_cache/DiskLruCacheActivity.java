package com.autoai.android.demos.disk_lru_cache;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoai.android.demos.R;

public class DiskLruCacheActivity extends Activity {

    private static final String TAG = "DiskLruCacheActivity";

    private static final int FILE_SIZE = 1024*100;

    private TextView textView;

    private Button button;

    private DiskCacheController diskCacheController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_lru_cache);

        textView = (TextView) findViewById(R.id.textView);

        findViewById(R.id.initButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(diskCacheController == null){
                    diskCacheController = new DiskCacheController(getApplicationContext());
                    diskCacheController.start();
                }
            }
        });

        findViewById(R.id.runButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(0);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (diskCacheController != null) {
            diskCacheController.stop();
        }

        mHandler.removeMessages(0);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (diskCacheController != null) {
                        mHandler.sendEmptyMessageDelayed(0, 1000);

                        // write file operation
                        byte[] bytes = new byte[FILE_SIZE];
                        int size = bytes.length;
                        String time = String.valueOf(System.currentTimeMillis());
                        String fileName = time.substring(0, time.length()-3);

                        diskCacheController.saveBitmap2File(bytes, size, fileName);
                        Log.e(TAG, "write file " + fileName);
                    }
                    break;
            }
        }
    };

}

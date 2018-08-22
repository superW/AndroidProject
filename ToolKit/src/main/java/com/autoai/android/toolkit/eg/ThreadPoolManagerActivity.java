package com.autoai.android.toolkit.eg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoai.android.toolkit.R;
import com.autoai.android.toolkit.ThreadPoolManager;

public class ThreadPoolManagerActivity extends Activity {

    private static final String TAG = "MainActivity";

    private TextView textView;

    private Button button;

    private ThreadPoolManager manager = ThreadPoolManager.newInstance();

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 100; i ++) {
                    manager.addExecuteTask(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                Log.e(TAG, "task" + (count++));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        manager.prepare();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.shutdown();

    }

}

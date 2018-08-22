package com.autoai.android.demos.thread_service_cycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoai.android.demos.R;

/**
 * 验证 线程 在 service服务 内 启动后，是否会跟随服务而消亡
 * 结论是，服务destroy后，线程仍继续执行（通过日志得出）
 */
public class ThreadServiceCycleActivity extends Activity {

    Button btnStartService;
    Button btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_service_cycle);


        btnStartService = (Button) findViewById(R.id.startService);
        btnStopService = (Button) findViewById(R.id.stopService);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreadServiceCycleActivity.this, ThreadService.class);
                startService(intent);
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreadServiceCycleActivity.this, ThreadService.class);
                stopService(intent);
            }
        });

    }
}

package com.autoai.android.demos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoai.android.demos.android_system_info.AndroidSystemInfoActivity;
import com.autoai.android.demos.disk_lru_cache.DiskLruCacheActivity;
import com.autoai.android.demos.load_dex_jar.LoadDexJarActivity;
import com.autoai.android.demos.net_state_modify.NetStateModifyActivity;
import com.autoai.android.demos.permission_manage.PermissionManageActivity;
import com.autoai.android.demos.thread_service_cycle.ThreadServiceCycleActivity;

public class MainActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        findViewById(R.id.AndroidSystemInfoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AndroidSystemInfoActivity.class));
            }
        });

        findViewById(R.id.DiskLruCacheBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DiskLruCacheActivity.class));
            }
        });

        findViewById(R.id.LoadDexJarBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoadDexJarActivity.class));
            }
        });

        findViewById(R.id.NetStateModifyBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NetStateModifyActivity.class));
            }
        });

        findViewById(R.id.PermissionManageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PermissionManageActivity.class));
            }
        });

        findViewById(R.id.ThreadServiceCycleBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ThreadServiceCycleActivity.class));
            }
        });


    }
}

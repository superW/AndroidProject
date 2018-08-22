package com.superw.foregroundservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStart= (Button) findViewById(R.id.btnStart);
        Button btnStop= (Button) findViewById(R.id.btnStop);
        Button btnOnDestroy= (Button) findViewById(R.id.btnOnDestroy);
        final Intent intent = new Intent(this,ForegroundService.class);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("cmd",0);//0,开启前台服务,1,关闭前台服务
                startService(intent);
            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("cmd",1);//0,开启前台服务,1,关闭前台服务
                startService(intent);
            }
        });

        btnOnDestroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });

        Button btnStart1= (Button) findViewById(R.id.btnStart1);
        Button btnStop1= (Button) findViewById(R.id.btnStop1);
        Button btnOnDestroy1= (Button) findViewById(R.id.btnOnDestroy1);
        final Intent intent1 = new Intent(this,InnerService.class);


        btnStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1.putExtra("cmd",0);//0,开启前台服务,1,关闭前台服务
                startService(intent1);
            }
        });


        btnStop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1.putExtra("cmd",1);//0,开启前台服务,1,关闭前台服务
                startService(intent1);
            }
        });

        btnOnDestroy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent1);
            }
        });



    }
}

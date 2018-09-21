package com.autoai.android.toolkit.eg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoai.android.toolkit.R;

public class MainActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        findViewById(R.id.commandExeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommandExecutionActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.easySpBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EasySPActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.fileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FileUtilsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.LogManagerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LogManagerActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.shellUtilsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShellUtilsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.storageUtilsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StorageUtilsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.threadPoolManagerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThreadPoolManagerActivity.class);
                startActivity(intent);
            }
        });

    }
}

package com.autoai.android.demos.android_system_info;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoai.android.demos.R;

public class AndroidSystemInfoActivity extends Activity {

    private TextView textView;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_system_info);

        textView = (TextView) findViewById(R.id.textView);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
    }

    private void init() {
        textView.append("获取设备基板名称 Build.BOARD = " + Build.BOARD + "\n"); // 获取设备基板名称
        textView.setText("设备版本号 Build.ID = " + Build.ID + "\n"); // 设备版本号
        textView.append("获取设备驱动名称 Build.DEVICE = " + Build.DEVICE + "\n"); // 获取设备驱动名称
        textView.append("获取设备显示的版本包 Build.DISPLAY = " + Build.DISPLAY + "\n"); // 获取设备显示的版本包（在系统设置中显示为版本号）和ID一样
        textView.append("设备硬件名称 Build.HARDWARE = " + Build.HARDWARE + "\n"); // 设备硬件名称,一般和基板名称一样（BOARD）
        textView.append("设备的唯一标识 Build.FINGERPRINT = " + Build.FINGERPRINT + "\n"); // 设备的唯一标识。由设备的多个信息拼接合成
        textView.append("整个产品的名称 Build.PRODUCT = " + Build.PRODUCT + "\n"); // 整个产品的名称
        textView.append("无线电固件版本号 Build.RADIO = " + Build.RADIO + "\n"); // 无线电固件版本号，通常是不可用的 显示unknown
        textView.append("获取系统版本字符串 Build.VERSION.RELEASE = " + Build.VERSION.RELEASE + "\n"); // 获取系统版本字符串。如4.1.2 或2.2 或2.3等
        textView.append("设备当前的系统开发代号 Build.VERSION.CODENAME = " + Build.VERSION.CODENAME + "\n"); // 设备当前的系统开发代号，一般使用REL代替
        textView.append("系统源代码控制值 Build.VERSION.INCREMENTAL = " + Build.VERSION.INCREMENTAL + "\n"); // 系统源代码控制值，一个数字或者git hash值
    }

}

package com.mapbar.timeintervalcontrol;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView textView;
    private TextView connectStateTV;

    private Button connect;
    private Button disconnect;
    private Button button;

    // 间隔时间
    private static final int TIME_INTERVAL = 10 * 1000;

    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("TAG", "handle message");
                    if (state == tempState) {
                        return;
                    }
                    Log.e("TAG", "change state");
                    state = tempState;
                    textView.setText(state ? "final state = true" : "final state = false");
                    break;
            }
        }
    };

    boolean state = true;

    boolean tempState = true;

    boolean isConnect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        connectStateTV = (TextView) findViewById(R.id.state);

        connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isConnect = true;
                connectStateTV.setText("connect state = true");
            }
        });

        disconnect = (Button) findViewById(R.id.disconnect);
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isConnect = false;
                connectStateTV.setText("connect state = false");
            }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("TAG", "click");
                // 如果间隔时间内的临时状态和需要修改的状态一致，不影响倒计时
                if (tempState == isConnect) return;
                Log.e("TAG", "start count down");
                tempState = isConnect;

                // 如果间隔时间内的临时状态和需要修改的状态不一致，重新计时
                mainHandler.removeMessages(1);
                Log.e("TAG", "removeMessages");
                mainHandler.sendEmptyMessageDelayed(1, TIME_INTERVAL);
                Log.e("TAG", "sendEmptyMessageDelayed");
            }
        });
    }

}

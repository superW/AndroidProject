package com.autoai.android.toolkit.eg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoai.android.toolkit.LogManager;
import com.autoai.android.toolkit.R;

public class LogManagerActivity extends Activity {

    private static final String TAG = "LogManagerActivity";

    private TextView textView;

    private Button button;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_manager);

        textView = (TextView) findViewById(R.id.textView);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LogManager.isLoggable()) {
                    LogManager.e(TAG, "Log " + (++count));
                    Log.e(TAG, "Log " + (++count));
                }
            }
        });
    }
}

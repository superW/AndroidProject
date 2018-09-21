package com.autoai.android.toolkit.eg;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.autoai.android.toolkit.CommandExecution;
import com.autoai.android.toolkit.R;

public class CommandExecutionActivity extends Activity {

    private TextView textView;
    private Button button;
    private ScrollView scrollView;
    private int lineNum;


    private String[] execCommands = new String[]{"cd /sys/bus/usb/devices/", "ls"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_execution);

        textView = (TextView) findViewById(R.id.textView);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        printCommand();

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommandExecution.CommandResult cr = CommandExecution.execCommand(execCommands, false);
                appendText("result=" + cr.result);
                appendText("successMsg=" + cr.successMsg);
                appendText("errorMsg=" + cr.errorMsg);
            }
        });
    }

    private void printCommand() {
        appendText("预执行命令：");
        for (String s : execCommands) {
            appendText(s);
        }
    }

    /**
     * TextView显示文字
     *
     * @param text
     */
    private synchronized void showText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(text + "\n");
            }
        });
    }

    /**
     * TextView追加文字
     *
     * @param text
     */
    private synchronized void appendText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.append((++lineNum) + " " + text + "\n");
            }
        });
        scrollToBottom();
    }

    public void scrollToBottom() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, textView.getBottom());
            }
        });
    }
}

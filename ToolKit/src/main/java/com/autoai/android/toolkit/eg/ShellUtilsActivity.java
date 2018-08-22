package com.autoai.android.toolkit.eg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoai.android.toolkit.R;
import com.autoai.android.toolkit.ShellUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShellUtilsActivity extends Activity {

    private TextView textView;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell_utils);

        textView = (TextView) findViewById(R.id.textView);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    run(new String[]{"/system/bin/am", "broadcast -a android.intent.action.USER_PRESENT"}, "/system/bin/");
//                    run(new String[]{"su", "broadcast -a android.intent.action.USER_PRESENT"}, "/system/bin/");
//                    Process p  = Runtime.getRuntime().exec("ls");
//                    Process p  = Runtime.getRuntime().exec("su am broadcast -a android.intent.action.USER_PRESENT");
//                    process(p);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            ShellUtils.execCmd("su am broadcast -a android.intent.action.USER_PRESENT", true, false);
                            ShellUtils.execCmd("am broadcast -a android.intent.action.USER_PRESENT", true, false);
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void process(Process p) throws IOException {
        String data = null;
        BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String error = null;
        while ((error = ie.readLine()) != null
                && !error.equals("null")) {
            data += error + "\n";
        }
        String line = null;
        while ((line = in.readLine()) != null
                && !line.equals("null")) {
            data += line + "\n";
        }

        Log.e("ls", data);
    }
    /**
     * 执行一个shell命令，并返回字符串值
     *
     * @param cmd
     * 命令名称&参数组成的数组（例如：{"/system/bin/cat", "/proc/version"}）
     * @param workdirectory
     * 命令执行路径（例如："system/bin/"）
     * @return 执行结果组成的字符串
     * @throws IOException
     */
    public static synchronized String run(String[] cmd, String workdirectory)
            throws IOException {
        StringBuffer result = new StringBuffer();
        try {
            // 创建操作系统进程（也可以由Runtime.exec()启动）
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(cmd);
//             InputStream inputstream = proc.getInputStream();
//            ProcessBuilder builder = new ProcessBuilder(cmd);
            InputStream in = null;
            // 设置一个路径（绝对路径了就不一定需要）
            if (workdirectory != null) {
                // 设置工作目录（同上）
//                builder.directory(new File(workdirectory));
//                // 合并标准错误和标准输出
//                builder.redirectErrorStream(true);
//                // 启动一个新进程
//                Process process = builder.start();

                // 读取进程标准输出流
//                in = process.getInputStream();
                in = proc.getInputStream();
                byte[] re = new byte[1024];
                while (in.read(re) != -1) {
                    result = result.append(new String(re));
                }
            }
            // 关闭输入流
            if (in != null) {
                in.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result.toString();
    }


}
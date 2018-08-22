package com.autoai.android.toolkit.eg;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoai.android.toolkit.FileUtils;
import com.autoai.android.toolkit.R;

import java.io.File;

public class FileUtilsActivity extends Activity {

    private static final String TAG = "FileUtilsActivity";

    private TextView textView;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_utils);

        textView = (TextView) findViewById(R.id.textView);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                StringBuilder sb = new StringBuilder();

                long folderSize = FileUtils.getFolderSize(new File("/mnt/sdcard/"));
                String folderSizeStr = Formatter.formatFileSize(getApplicationContext(), folderSize);

                long fileSize = FileUtils.getFileSize(new File("/mnt/sdcard/Download/name.apk"));
                String fileSizeStr = Formatter.formatFileSize(getApplicationContext(), fileSize);

                String result = "onClick: folder size=" + folderSizeStr + ", file size=" + fileSizeStr;

                textView.setText(result);
                Log.e(TAG, result);
            }
        });
    }
}

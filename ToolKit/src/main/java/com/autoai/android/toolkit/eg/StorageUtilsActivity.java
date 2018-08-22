package com.autoai.android.toolkit.eg;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoai.android.toolkit.R;
import com.autoai.android.toolkit.StorageUtils;

/**
 *
 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 */
public class StorageUtilsActivity extends Activity {

    private TextView textView;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_utils);

        textView = (TextView) findViewById(R.id.textView);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                sb.append("path=" + StorageUtils.STORAGE_PATH)
                        .append(", totalSize=")
                        .append(format(StorageUtils.getTotalSpace(StorageUtils.STORAGE_PATH)))
                        .append(", availableSize=")
                        .append(format(StorageUtils.getAvailableSpace(StorageUtils.STORAGE_PATH)))
                        .append(", usedSize=")
                        .append(format(StorageUtils.getUsedSpace(StorageUtils.STORAGE_PATH)));
                textView.setText(sb.toString());
            }
        });
    }

    private String format(long ori) {
        return Formatter.formatFileSize(this, ori);
    }
}

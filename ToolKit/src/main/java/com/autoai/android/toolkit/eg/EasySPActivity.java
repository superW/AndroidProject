package com.autoai.android.toolkit.eg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoai.android.toolkit.EasySP;
import com.autoai.android.toolkit.R;

public class EasySPActivity extends Activity {

    private TextView textView;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_sp);

    }

    public void clickPut(View view) {
        EasySP.init(this).putString(R.string.app_name, "test success").commit();
        EasySP.init(this).putInt("int", 100).commit();
        EasySP.init(this).putBoolean("bool", true).commit();
//        EasySP.init(this).put("bool", false).commit();
    }

    public void clickGet(View view) {
        String name = EasySP.init(this).getString(R.string.app_name, "test failed");
        int num = EasySP.init(this).getInt("int");
        boolean bool = EasySP.init(this).getBoolean("bool");
        TextView tvShow = (TextView) findViewById(R.id.tvShow);
        tvShow.setText(name + ", " + num +
//                ", " + bool +
                ", " + bool);
    }

    public void clickClear(View view) {
        EasySP.init(this).clear().commit();
    }

    public void clickRemove(View view) {
        EasySP.init(this).remove(R.string.app_name).commit();
    }

}

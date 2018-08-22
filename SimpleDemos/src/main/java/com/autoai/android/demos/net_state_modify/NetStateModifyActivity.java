package com.autoai.android.demos.net_state_modify;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;

import com.autoai.android.demos.R;

public class NetStateModifyActivity extends Activity {

    NetStateModifyReceiver netStateModifyReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_state_modify);

        netStateModifyReceiver = new NetStateModifyReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");

        registerReceiver(netStateModifyReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netStateModifyReceiver);
    }
}

package com.superw.androidproject.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.superw.androidproject.R;


public class LoadingHelper {

    private View loadingView;

    public LoadingHelper(Context context) {
//        loadingView = LayoutInflater.from(context)
//                .inflate(R.layout.loading, null);
        hide();
    }

    public View getLoadingView() {
        return loadingView;
    }

    public void show() {
        if (loadingView != null && !isShowing()) {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    public void hide() {
        if (loadingView != null && isShowing()) {
            loadingView.setVisibility(View.GONE);
        }
    }

    public boolean isShowing() {
        return loadingView.getVisibility() == View.VISIBLE;
    }
}

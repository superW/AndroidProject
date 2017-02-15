package com.superw.androidproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.superw.androidproject.base.BasePresenterImpl;
import com.superw.androidproject.presenter.intf.SplashPresenter;
import com.superw.androidproject.view.SplashView;

/**
 * Created by wangyanchao on 2017/2/15.
 */

public class SplashPresenterImpl extends BasePresenterImpl<SplashView> implements SplashPresenter {
    @Override
    public void loading() {
        mView.showLoading();
        new Handler(Looper.getMainLooper())
                .postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.hideLoading();
                mView.jumpToMain();
            }
        }, 3000);
    }
}

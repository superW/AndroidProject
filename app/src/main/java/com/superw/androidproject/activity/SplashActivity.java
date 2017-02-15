package com.superw.androidproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.superw.androidproject.R;
import com.superw.androidproject.base.BaseActivity;
import com.superw.androidproject.presenter.SplashPresenterImpl;
import com.superw.androidproject.presenter.intf.SplashPresenter;
import com.superw.androidproject.view.SplashView;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashView {


    @Override
    protected SplashPresenter getPresenter() {
        return mPresenter != null ? mPresenter : new SplashPresenterImpl();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews(Bundle savedInstance) {
        mPresenter.loading();
    }

    @Override
    public void jumpToMain() {
        hideLoading();
        startActivity(new Intent(this, MainActivity.class));
    }
}

package com.superw.androidproject.activity;

import android.os.Bundle;
import android.os.Handler;

import com.superw.androidproject.base.BaseActivity;
import com.superw.androidproject.R;
import com.superw.androidproject.presenter.MainPresenterImpl;
import com.superw.androidproject.presenter.intf.MainPresenter;
import com.superw.androidproject.view.MainView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {


    @Override
    protected MainPresenter getPresenter() {
        return mPresenter != null ? mPresenter : new MainPresenterImpl();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstance) {

    }

}

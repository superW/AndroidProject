package com.superw.androidproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.superw.androidproject.component.LoadingHelper;
import com.superw.androidproject.util.TShow;

/**
 * Created by wangyanchao on 2017/2/15.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    private LoadingHelper loadingHelper;


    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = getPresenter();
        if (mPresenter != null)
            mPresenter.attachView(this);

        setContentView(getLayoutId());

        loadingHelper = new LoadingHelper(this);
        FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
        decorView.addView(loadingHelper.getLoadingView());

        initViews(savedInstanceState);
    }

    protected abstract P getPresenter();

    protected abstract int getLayoutId();

    protected abstract void initViews(Bundle savedInstance);

    public <T extends View> T getViewById(int resId) {
        return (T) super.findViewById(resId);
    }

    @Override
    public void showToast(String msg) {
        TShow.shortTime(this, msg);
    }

    @Override
    public void showLoading() {
        loadingHelper.show();
    }

    @Override
    public void hideLoading() {
        loadingHelper.hide();
    }

    @Override
    public void updateUI() {

    }
}

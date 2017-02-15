package com.superw.androidproject.base;

/**
 * Created by wangyanchao on 2017/2/15.
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {

    protected V mView;

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public void refreshViews() {
        mView.updateUI();
    }
}

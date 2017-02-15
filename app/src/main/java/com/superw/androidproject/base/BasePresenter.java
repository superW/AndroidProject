package com.superw.androidproject.base;

/**
 * Created by wangyanchao on 2017/2/15.
 */

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);

    void detachView();

    void refreshViews();

}

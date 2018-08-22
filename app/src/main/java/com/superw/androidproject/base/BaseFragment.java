package com.superw.androidproject.base;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.superw.androidproject.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements BaseView {

    protected View rootView;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initViews(rootView);
        return rootView;
    }

    /**
     * 布局文件ID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化各种控件
     */
    protected abstract void initViews(View rootView);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showToast(String msg) {
        ((BaseActivity) getActivity()).showToast(msg);
    }

    @Override
    public void showLoading() {
        ((BaseActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((BaseActivity) getActivity()).hideLoading();
    }

    @Override
    public void updateUI() {
        ((BaseActivity) getActivity()).updateUI();
    }


}

package com.superw.androidproject.activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superw.androidproject.R;
import com.superw.androidproject.base.BaseActivity;
import com.superw.androidproject.presenter.MainPresenterImpl;
import com.superw.androidproject.presenter.intf.MainPresenter;
import com.superw.androidproject.view.MainView;


import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {


    @BindView(R.id.root_layout)
    FrameLayout rootLayout;
    @BindView(R.id.main_tab1)
    TextView mainTab1;
    @BindView(R.id.main_tab2)
    TextView mainTab2;
    @BindView(R.id.main_tab3)
    TextView mainTab3;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.text)
    TextView text;



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

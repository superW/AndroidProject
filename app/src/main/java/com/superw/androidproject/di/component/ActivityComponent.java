package com.superw.androidproject.di.component;

import android.app.Activity;

import com.superw.androidproject.activity.MainActivity;
import com.superw.androidproject.activity.SplashActivity;
import com.superw.androidproject.di.ActivityScope;
import com.superw.androidproject.di.module.ActivityModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

}

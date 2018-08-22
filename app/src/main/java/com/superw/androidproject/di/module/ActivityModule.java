package com.superw.androidproject.di.module;

import android.app.Activity;

import com.superw.androidproject.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wangyanchao on 2017/2/23.
 */

@Module
public class ActivityModule {

    Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return activity;
    }
}

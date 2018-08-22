package com.superw.androidproject.di.module;

import com.superw.androidproject.common.App;
import com.superw.androidproject.component.HttpHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper() {
        return new HttpHelper();
    }
}

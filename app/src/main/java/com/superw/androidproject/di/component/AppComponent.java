package com.superw.androidproject.di.component;

import com.superw.androidproject.common.App;
import com.superw.androidproject.component.HttpHelper;
import com.superw.androidproject.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    App getAppInstance();

    HttpHelper httpHelper();
}

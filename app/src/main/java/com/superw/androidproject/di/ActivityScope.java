package com.superw.androidproject.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by wangyanchao on 2017/2/23.
 */

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}

/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.klisly.bookbox;

import com.google.gson.Gson;
import com.klisly.bookbox.utils.ToastHelper;
import com.klisly.common.SharedPreferenceUtils;

import android.app.Application;
import timber.log.Timber;

public class BookBoxApplication extends Application {
    private static BookBoxApplication appContext = null;
    private SharedPreferenceUtils preferenceUtils;
    public static BookBoxApplication getInstance() {
        return appContext;
    }
    private Gson gson = new Gson();
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        Timber.plant(new Timber.DebugTree());
        ToastHelper.init(this);
        preferenceUtils = new SharedPreferenceUtils(this);
    }

    public SharedPreferenceUtils getPreferenceUtils() {
        return preferenceUtils;
    }

    public Gson getGson() {
        return gson;
    }
}

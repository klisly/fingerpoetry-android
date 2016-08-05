/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.klisly.bookbox;

import android.app.Application;
import android.os.Handler;

import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.klisly.bookbox.utils.ToastHelper;
import com.klisly.common.SharedPreferenceUtils;

import timber.log.Timber;

public class BookBoxApplication extends Application {
    private static BookBoxApplication appContext = null;
    private SharedPreferenceUtils preferenceUtils;
    public static BookBoxApplication getInstance() {
        return appContext;
    }
    private Gson gson = new Gson();
    private Handler handler;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        Timber.plant(new Timber.DebugTree());
        ToastHelper.init(this);
        Dexter.initialize(this);
        preferenceUtils = new SharedPreferenceUtils(this);
        handler = new Handler();
    }

    public SharedPreferenceUtils getPreferenceUtils() {
        return preferenceUtils;
    }

    public Gson getGson() {
        return gson;
    }

    public Handler getHandler() {
        return handler;
    }
}

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
import com.tencent.bugly.crashreport.CrashReport;

import org.lasque.tusdk.core.TuSdk;

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
        TuSdk.init(this.getApplicationContext(), "492658840c3a4925-00-h5ptp1");
        TuSdk.enableDebugLog(false);
        CrashReport.initCrashReport(getApplicationContext(), "900028744", false);
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

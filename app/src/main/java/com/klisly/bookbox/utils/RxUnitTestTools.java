package com.klisly.bookbox.utils;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

public class RxUnitTestTools {

    private static boolean isInitRxTools = false;

    /**
     * 把异步变成同步，方便测试
     */
    public static void openRxTools() {
        if (isInitRxTools) {
            return;
        }
        isInitRxTools = true;

        RxAndroidSchedulersHook rxAndroidSchedulersHook = new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        };

        RxJavaSchedulersHook rxJavaSchedulersHook = new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        };

        RxAndroidPlugins.getInstance().registerSchedulersHook(rxAndroidSchedulersHook);
        RxJavaPlugins.getInstance().registerSchedulersHook(rxJavaSchedulersHook);
    }

}

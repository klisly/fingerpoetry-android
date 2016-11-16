/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.klisly.bookbox.utils;

import android.content.Context;
import android.net.Uri;

public class ActivityUtil {

    public static Uri getAppResourceUri(int resId, String packageName) {
        return Uri.parse("android.resource://" + packageName + "/" + resId);
    }

    /**
     * dpתpx
     *
     */
    public static int dip2px(Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     *	pxתdp
     */
    public static int px2dip(Context ctx,float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}

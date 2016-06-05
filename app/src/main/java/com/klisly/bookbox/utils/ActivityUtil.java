/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.klisly.bookbox.utils;

import android.net.Uri;

public class ActivityUtil {

    public static Uri getAppResourceUri(int resId, String packageName) {
        return Uri.parse("android.resource://" + packageName + "/" + resId);
    }
}

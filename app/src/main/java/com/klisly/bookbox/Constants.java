package com.klisly.bookbox;

import com.klisly.common.SharedPreferenceUtils;

public class Constants {
    // 频道类型
    public static final int HOME = 0;
    public static final int SITE = 1;
    public static final int MAGEZINE = 2;
    public static final int TOPIC = 3;
    public static final int DEFAULT_TOPIC_SIZE = 6;

    // MOB设置
    public static final String MOB_APP_KEY = "13818ffc591c0";
    public static final String MOB_APP_SECRET = "2934671139c5ff13e26f5be2381fc35f";
    public static final String ZH_ZONE_CODE = "+86";

    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int INVALID = -1;

    public static final String KEY_FIRST_LAUNCH = "KEY_FIRST_LAUNCH";

    public static final String RESERVE_TOPIC_HOT = "热门";
    public static final String RESERVE_TOPIC_RECOMMEND = "推荐";
    public static final long MAX_REQUEST_TIME = 30000;
    public static final String INVALID_ARTICLE_ID = "INVALID_ARTICLE_ID";


    private static SharedPreferenceUtils preferenceUtils = BookBoxApplication.getInstance().getPreferenceUtils();

    public static void setFirstLaunch(boolean isFirst) {
        preferenceUtils.setValue(KEY_FIRST_LAUNCH, isFirst);
    }

    public static boolean isFirstLaunch() {
        return preferenceUtils.getValue(KEY_FIRST_LAUNCH, true);
    }
    // key
    public static String PAGE = "page";
    public static String PAGE_SIZE = "pageSize";
    public static String BEFORE_AT = "beforeAt";
    public static String AFTER_AT = "afterAt";
    public static String TYPE = "type";

    public static String PLATFORM_LOCAL = "LOCAL";

    public static String QQ_APP_ID = "1105643926";
    public static final String SplashPosID = "8000011512009668";

    public static int ITEM_TYPE_NORMAL = 1;
    public static int ITEM_TYPE_JOKE = 2;
    public static String LAST_CHECK = "LAST_CHECK";
    public static long UPDATE_CHECK_DURATION = 86400000;
}

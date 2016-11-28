package com.klisly.bookbox;

import com.klisly.common.SharedPreferenceUtils;

public class Constants {
    // 频道类型
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

    public static final String NOTIFI_TYPE_MOMENT = "moment"; // 朝花夕拾
    public static final String NOTIFI_TYPE_NOVEL_UPDATE = "novelupate"; // 小说更新
    public static final int NOTIFI_ACTION_MOMENT = 1;
    public static final int NOTIFI_ACTION_NOVEL_UPDATE = 2;
    public static final int NOTIFI_ID_MOMENT = 1;
    public static final int NOTIFI_ID_NOVEL_UPDATE = 2;

    private static SharedPreferenceUtils preferenceUtils = BookBoxApplication.getInstance().getPreferenceUtils();

    public static void setFirstLaunch(boolean isFirst) {
        preferenceUtils.setValue(KEY_FIRST_LAUNCH, isFirst);
    }

    public static boolean isFirstLaunch() {
        return preferenceUtils.getValue(KEY_FIRST_LAUNCH, true);
    }
    // key

    public static String PLATFORM_LOCAL = "LOCAL";

    public static String QQ_APP_ID = "1105643926";
    public static final String SplashPosID = "8000011512009668";
    public static final String BannerPosId = "3070210722867769";

    public static int ITEM_TYPE_NORMAL = 1;
    public static int ITEM_TYPE_JOKE = 2;
    public static String LAST_CHECK = "LAST_CHECK";
    public static long UPDATE_CHECK_DURATION = 86400000;
}

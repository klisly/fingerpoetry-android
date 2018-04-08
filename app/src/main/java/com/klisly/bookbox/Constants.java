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
    public static final String ARTICLE_PREFIX = "<!DOCTYPE html><html><head><title>指尖书香</title><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">"
            + "<meta name=\"viewport\" content=\"width=device-width, maximum-scale=1.0,  maximum-scale=1.0, user-scalable=no\">"
            + " <style type=\"text/css\">"
            + " body {"
            + "margin: 0;"
            + "padding-left: 10px;padding-right:10px; "
            + " font: 16px"
            + "background: #F5FCFF;"
            + "} "
            + " a{"
            + " text-decoration:none;"
            + "  outline:0 none;pointer-events:none; color:inherit; cursor:default; "
            + " }"
            + " p { "
            + "marginTop: 5; "
            + "}  img{display: block;\n" +
            "    width: 100%;} "
            + "</style> "
            + "</head> "
            + "<body> ";
    public static final String ARTICLE_SUFFIX = "</body> "
            + " </html>";
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
    public static final String FIRST_OPEN = "first_open";
    public static final String HOME_FRAG = "HOME_FRAG";
    public static final String FRAG_TOPIC = "topic";
    public static final String FRAG_SITE = "site";
    public static final String FRAG_MAGZINE = "magzine";
    public static final String FRAG_WX = "wx";
    public static final String FRAG_NOVEL = "novel";
    public static final String NOVEL_END_0 = "&nbsp;&nbsp;&nbsp;&nbsp;一秒记住【笔趣阁中文网\n" +
            "<a href=\"http://www.biqugezw.com\" target=\"_blank\">www.biqugezw.com</a>】，为您提供精彩小说阅读。\n" +
            "<br> \n" +
            "<br> ";
    public static final String NOVEL_END_1 = "&#xA0;&#xA0;&#xA0;&#xA0;&#x4E00;&#x79D2;&#x8BB0;&#x4F4F;&#x3010;&#x7B14;&#x8DA3;&#x9601;&#x4E2D;&#x6587;&#x7F51;<a href=\"http://www.biqugezw.com\" target=\"_blank\">www.biqugezw.com</a>&#x3011;&#xFF0C;&#x4E3A;&#x60A8;&#x63D0;&#x4F9B;&#x7CBE;&#x5F69;&#x5C0F;&#x8BF4;&#x9605;&#x8BFB;&#x3002;<br>\n" +
            "<br>";
    public static final String NOVEL_END_2 = "手机用户请浏览m.biqugezw.com阅读，更优质的阅读体验。";
    public static final String NOVEL_END_3 = "&#x624B;&#x673A;&#x7528;&#x6237;&#x8BF7;&#x6D4F;&#x89C8;m.biqugezw.com&#x9605;&#x8BFB;&#xFF0C;&#x66F4;&#x4F18;&#x8D28;&#x7684;&#x9605;&#x8BFB;&#x4F53;&#x9A8C;&#x3002;";

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

    public static String getCharset(String url) {
        if(url.indexOf("biqugezw.com")!= -1){
            return "gbk";
        }
        return "utf-8";
    }

    public static String NOTIFICATION_MORNING = "NOTIFICATION_MORNING";
    public static String NOTIFICATION_AFTERNOON = "NOTIFICATION_AFTERNOON";
    public static String NOTIFICATION_NOVEL = "NOTIFICATION_NOVEL";

    public static final String DEFAULT_WX_CATES = "DEFAULT_WX_CATES";

}

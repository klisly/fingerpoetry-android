package com.klisly.bookbox.api;

import android.test.mock.MockContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.logic.AccountLogic;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static anetwork.channel.http.NetworkSdkSetting.context;

public class BookRetrofit {
//    public final static String BASE_URL = "http://192.168.1.100:3000/v1/";
        public final static String BASE_URL = "https://second.imdao.cn/v1/";
    public final static String BASE_API_URL = BASE_URL;

    private final static long DEFAULT_TIMEOUT = 15; // 15s超时
    private final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .serializeNulls()
            .create();
    private static BookRetrofit instance;
    private AccountApi accountApi;
    private SiteApi siteApi;
    private TopicApi topicApi;
    private ArticleApi articleApi;
    private WxArticleApi wxArticleApi;
    private SysApi sysApi;
    private NovelApi novelApi;
    private OkHttpClient okHttpClient;

    public BookRetrofit() {
        if(BookBoxApplication.getInstance()!=null){
            context = BookBoxApplication.getInstance().getApplicationContext();
        } else {
            context = new MockContext();
        }
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //设置缓存目录
        try {
            if(context.getCacheDir() != null){
                File cacheDirectory = new File(context.getCacheDir()
                        .getAbsolutePath(), "HttpCache");
                Cache cache = new Cache(cacheDirectory, 20 * 1024 * 1024);
                httpClientBuilder.cache(cache);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        okHttpClient = httpClientBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        accountApi = retrofit.create(AccountApi.class);
        siteApi = retrofit.create(SiteApi.class);
        topicApi = retrofit.create(TopicApi.class);
        articleApi = retrofit.create(ArticleApi.class);
        sysApi = retrofit.create(SysApi.class);
        novelApi = retrofit.create(NovelApi.class);
        wxArticleApi = retrofit.create(WxArticleApi.class);
    }

    /**
     * BookRetrofit
     *
     * @return single instance of ConversationLogic
     */
    public static BookRetrofit getInstance() {
        if (instance == null) {
            synchronized(AccountLogic.class) {
                if (instance == null) {
                    instance = new BookRetrofit();
                }
            }
        }
        return instance;
    }

    public AccountApi getAccountApi() {
        return accountApi;
    }

    public SiteApi getSiteApi() {
        return siteApi;
    }

    public TopicApi getTopicApi() {
        return topicApi;
    }

    public ArticleApi getArticleApi() {
        return articleApi;
    }

    public NovelApi getNovelApi() {
        return novelApi;
    }

    public SysApi getSysApi() {
        return sysApi;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public WxArticleApi getWxArticleApi() {
        return wxArticleApi;
    }
}

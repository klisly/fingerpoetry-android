package com.klisly.bookbox.api;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.logic.AccountLogic;

import android.content.Context;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRetrofit {
    public final static String BASE_URL = "http://192.168.10.102:3000/api/";
    private final static long DEFAULT_TIMEOUT = 15; // 15超时
    private final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .serializeNulls()
            .create();
    private static BookRetrofit instance;
    private AccountApi accountApi;
    private ChannelApi channelApi;
    public BookRetrofit() {
        Context context = BookBoxApplication.getInstance().getApplicationContext();
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //设置缓存目录
        File cacheDirectory = new File(context.getCacheDir()
                .getAbsolutePath(), "HttpCache");
        Cache cache = new Cache(cacheDirectory, 20 * 1024 * 1024);
        httpClientBuilder.cache(cache);
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        accountApi = retrofit.create(AccountApi.class);
        channelApi = retrofit.create(ChannelApi.class);
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

    public ChannelApi getChannelApi() {
        return channelApi;
    }
}

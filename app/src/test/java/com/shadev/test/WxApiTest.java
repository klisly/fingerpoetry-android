/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.shadev.test;

import com.klisly.bookbox.BuildConfig;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.LoginData;
import com.klisly.bookbox.model.WxArticle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class WxApiTest {
    BookRetrofit bookRetrofit = null;
    Observer<ApiResult<LoginData>> observer = new Observer<ApiResult<LoginData>>() {
        @Override
        public void onCompleted() {
            System.out.println("onCompleted");

        }

        @Override
        public void onError(Throwable e) {
            System.out.println("onError：" + e.getMessage());

        }

        @Override
        public void onNext(ApiResult<LoginData> data) {
            System.out.println("datas:" + data);
        }
    };

    @Before
    public void setUp() {
        bookRetrofit = BookRetrofit.getInstance();
    }

    @Test
    public void page1() throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("tag", "热门");
        map.put("page", "1");
        map.put("pageSize", String.valueOf(20));
        Observable<ApiResult<List<WxArticle>>> observable = bookRetrofit.getWxArticleApi().list(map);
        observable.observeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.immediate())
                .subscribe(new Observer<ApiResult<List<WxArticle>>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError：" + e.getMessage());

                    }

                    @Override
                    public void onNext(ApiResult<List<WxArticle>> data) {
                        System.out.println("datas:" + data);
                        Assert.assertEquals(20, data.getData().size());
                    }
                });
    }
}

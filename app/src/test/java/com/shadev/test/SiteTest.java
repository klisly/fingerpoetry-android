/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.shadev.test;

import com.klisly.bookbox.BuildConfig;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Site;
import com.klisly.bookbox.utils.RxUnitTestTools;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.List;

import rx.Observer;

/**
 * Created by devinshine on 15/9/4.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SiteTest {
    BookRetrofit bookRetrofit = null;
    private String token;
    @Before
    public void setUp() {
        RxUnitTestTools.openRxTools();
        bookRetrofit = BookRetrofit.getInstance();
    }

    @Test
    public void listSites() throws IOException {
        bookRetrofit.getSiteApi().list().subscribe(new Observer<ApiResult<List<Site>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ApiResult<List<Site>> listApiResult) {
                System.out.println(listApiResult);
            }
        });
    }


}

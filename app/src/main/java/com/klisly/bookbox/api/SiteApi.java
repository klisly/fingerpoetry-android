package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Site;

import java.util.List;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface SiteApi {

    @GET("sites/")
    Observable<ApiResult<List<Site>>> list();

    @GET("sites/subscirbes/{uid}")
    Observable<ApiResult<List<Site>>> listsubscribes(@Path("uid") String uid);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("sites/{id}/subscirbe")
    Observable<ApiResult<Site>> subscribe(@Path("id") String id,
                                               @Header("x-access-token") String token);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("sites/{id}/subscirbe")
    Observable<ApiResult<Site>> unsubscribe(@Path("id") String id,
                                               @Header("x-access-token") String token);
}

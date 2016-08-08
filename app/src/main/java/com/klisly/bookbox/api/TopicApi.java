package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Topic;

import java.util.List;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface TopicApi {

    @GET("topics/")
    Observable<ApiResult<List<Topic>>> list();

    @Headers("Content-Type: application/json")
    @GET("topics/subscirbes/{uid}")
    Observable<ApiResult<List<Topic>>> subscribes(@Path("uid") String uid);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("topics/{id}/subscirbe")
    Observable<ApiResult<Topic>> subscribe(@Path("id") String id,
                                               @Header("x-access-token") String token);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("topics/{id}/subscirbe")
    Observable<ApiResult<Topic>> unsubscribe(@Path("id") String id,
                                                 @Header("x-access-token") String token);
}

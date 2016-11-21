package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Chapter;
import com.klisly.bookbox.model.Novel;
import com.klisly.bookbox.model.User2Topic;

import java.util.List;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface NovelApi {

    @Headers("Content-Type: application/json")
    @GET("users/{uid}/chapters")
    Observable<ApiResult<List<Chapter>>> dailyUpdates(@Path("uid") String uid,
                                                    @Header("x-access-token") String token);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @PUT("users/{uid}/novels")
    Observable<ApiResult<Novel>> novels(@Path("uid") String uid,
                                        @Header("x-access-token") String token);

    @GET("novels/chapters/{id}")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<Chapter>> fetch(@Path("id") String id, @Query("uid") String uid);


    @POST("novels/{id}/subscribe")
    Observable<ApiResult<User2Topic>> subscribe(@Path("id") String id,
                                                @Header("x-access-token") String token);

    @POST("novels/{id}/unsubscribe")
    Observable<ApiResult<User2Topic>> unsubscribe(@Path("id") String id,
                                                  @Header("x-access-token") String token);
}

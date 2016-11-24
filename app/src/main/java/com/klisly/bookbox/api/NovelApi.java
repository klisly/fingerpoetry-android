package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Chapter;
import com.klisly.bookbox.model.Novel;
import com.klisly.bookbox.model.User2Novel;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface NovelApi {

    @Headers("Content-Type: application/json")
    @GET("users/{uid}/chapters")
    Observable<ApiResult<List<Chapter>>> dailyUpdates(@Path("uid") String uid,
                                                    @Header("x-access-token") String token);

    @Headers("Content-Type:application/json")
    @GET("users/{uid}/novels")
    Observable<ApiResult<List<User2Novel>>> novels(@Path("uid") String uid,
                                        @Header("x-access-token") String token);
    @Headers("Content-Type:application/json")
    @GET("novels/recommend")
    Observable<ApiResult<List<Novel>>> recommend();

    @Headers("Content-Type:application/json")
    @GET("novels/search")
    Observable<ApiResult<List<Novel>>> search(@Query("name") String name);

    @GET("novels/chapters/{id}")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<Chapter>> fetch(@Path("id") String id, @Query("uid") String uid);

    @Headers("Content-Type:application/json")
    @POST("novels/subscribe")
    Observable<ApiResult<User2Novel>> subscribe(@Body Novel novel,
                                                @Header("x-access-token") String token);
    @Headers("Content-Type:application/json")
    @POST("novels/{id}/subscribe")
    Observable<ApiResult<User2Novel>> subscribeById(@Path("id") String id,
                                                    @Header("x-access-token") String token);
    @Headers("Content-Type:application/json")
    @POST("novels/{id}/unsubscribe")
    Observable<ApiResult<User2Novel>> unsubscribe(@Path("id") String id,
                                                  @Header("x-access-token") String token);
    @GET("novels/{id}/chapters")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<List<Chapter>>> listchapters(@Path("id") String id, @QueryMap Map<String, String> conditions);

}

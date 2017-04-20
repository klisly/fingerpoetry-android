package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.ArticleData;
import com.klisly.bookbox.model.Article;
import com.klisly.bookbox.model.Magazine;
import com.klisly.bookbox.model.User2Article;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ArticleApi {

    @GET("articles/magzines")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<List<Magazine>>> listMagazine(@QueryMap Map<String, String> conditions);


    @GET("articles/")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<List<Article>>> list(@QueryMap Map<String, String> conditions);

    @GET("articles/{id}")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<ArticleData>> fetch(@Path("id") String id, @Query("uid") String uid);

    @GET("articles/magzines")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<List<Article>>> mags(@QueryMap Map<String, String> conditions);

    @GET("users/{id}/collects")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<List<User2Article>>> collects(@Path("id") String id, @QueryMap Map<String, String> conditions);

    @GET("users/{id}/reads")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<User2Article>> reads(@Path("id") String id, @Query("uid") String uid);

    @POST("articles/{id}/heart")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<User2Article>> heart(@Path("id") String id, @Header("x-access-token") String token);

    @POST("articles/{id}/unheart")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<User2Article>> unheart(@Path("id") String id, @Header("x-access-token") String token);

    @POST("articles/{id}/toread")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<User2Article>> toread(@Path("id") String id, @Header("x-access-token") String token);

    @POST("articles/{id}/untoread")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<User2Article>> untoread(@Path("id") String id, @Header("x-access-token") String token);

    @POST("articles/{id}/collect")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<User2Article>> collect(@Path("id") String id, @Header("x-access-token") String token);

    @POST("articles/{id}/uncollect")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<User2Article>> uncollect(@Path("id") String id, @Header("x-access-token") String token);

}

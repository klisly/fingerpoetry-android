package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.ArticleData;
import com.klisly.bookbox.model.User2Article;
import com.klisly.bookbox.model.User2WxArticle;
import com.klisly.bookbox.model.WxArticle;

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

public interface WxArticleApi {
    @GET("wxarticles/")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<List<WxArticle>>> list(@QueryMap Map<String, String> conditions);

    @GET("wxarticles/{id}")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<WxArticle>> fetch(@Path("id") String id);

    @POST("wxarticles/{id}/collect")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<User2WxArticle>> collect(@Path("id") String id, @Header("x-access-token") String token);

    @POST("wxarticles/{id}/uncollect")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<User2WxArticle>> uncollect(@Path("id") String id, @Header("x-access-token") String token);

    @GET("wxarticles/collected")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<List<User2WxArticle>>> listCollected(@QueryMap Map<String, String> conditions);

    @GET("wxarticles/collectstatus/{id}/{uid}")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<User2WxArticle>> collectStatus(@Path("id") String id, @Path("uid") String uid);

}

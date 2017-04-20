package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
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
import retrofit2.http.QueryMap;
import rx.Observable;

public interface WxArticleApi {
    @GET("wxarticles/")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<List<WxArticle>>> list(@QueryMap Map<String, String> conditions);

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

}

package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.WxArticle;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface WxArticleApi {
    @GET("wxarticles/")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<List<WxArticle>>> list(@QueryMap Map<String, String> conditions);

}

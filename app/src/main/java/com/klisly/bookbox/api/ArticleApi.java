package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Article;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ArticleApi {
    @GET("articles/")
    Observable<ApiResult<List<Article>>> list(@QueryMap Map<String, String> conditions);

    @GET("articles/{id}")
    Observable<ApiResult<Article>> fetch(@Path("id") String id);
}

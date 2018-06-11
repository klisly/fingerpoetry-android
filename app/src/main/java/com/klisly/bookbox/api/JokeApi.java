package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Joke;
import com.klisly.bookbox.model.Site;
import com.klisly.bookbox.model.User2Site;
import com.klisly.bookbox.model.WxArticle;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface JokeApi {

    @GET("joke/")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<List<Joke>>> list(@QueryMap Map<String, String> conditions);

}

package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Version;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface SysApi {

    @GET("versions")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<Version>> fetch(@Query("type") String uid);

}

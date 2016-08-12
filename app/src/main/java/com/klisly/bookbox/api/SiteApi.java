package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Site;
import com.klisly.bookbox.model.User2Site;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface SiteApi {

    @GET("sites/")
    Observable<ApiResult<List<Site>>> list();

    @Headers("Content-Type: application/json")
    @GET("users/{uid}/sites")
    Observable<ApiResult<List<User2Site>>> subscribes(@Path("uid") String uid,
                                                      @Header("x-access-token") String token);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @PUT("users/{uid}/sites/reorder")
    Observable<ApiResult<Void>> reorder(@Path("uid") String uid,
                                        @Field("data") String data,
                                        @Header("x-access-token") String token);

    @POST("sites/{id}/subscribe")
    Observable<ApiResult<User2Site>> subscribe(@Path("id") String id,
                                                @Header("x-access-token") String token);

    @POST("sites/{id}/unsubscribe")
    Observable<ApiResult<User2Site>> unsubscribe(@Path("id") String id,
                                                  @Header("x-access-token") String token);
}

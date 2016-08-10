package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.model.Topic;
import com.klisly.bookbox.model.User2Topic;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface TopicApi {

    @GET("topics/")
    Observable<ApiResult<List<Topic>>> list();

    @Headers("Content-Type: application/json")
    @GET("users/{uid}/topics")
    Observable<ApiResult<List<User2Topic>>> subscribes(@Path("uid") String uid,
                                                  @Header("x-access-token") String token);

    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @GET("users/{uid}/topics/order")
    Observable<ApiResult<Void>> reorder(@FieldMap Map<String, Integer> uid,
                                                       @Header("x-access-token") String token);

    @POST("topics/{id}/subscribe")
    Observable<ApiResult<User2Topic>> subscribe(@Path("id") String id,
                                                @Header("x-access-token") String token);

    @PUT("topics/{id}/subscribe")
    Observable<ApiResult<Topic>> order(@Path("id") String id,
                                             @Header("x-access-token") String token);

    @POST("topics/{id}/unsubscribe")
    Observable<ApiResult<User2Topic>> unsubscribe(@Path("id") String id,
                                                 @Header("x-access-token") String token);
}

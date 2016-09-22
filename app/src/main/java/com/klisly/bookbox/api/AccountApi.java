package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.LoginData;
import com.klisly.bookbox.model.User;
import com.klisly.bookbox.model.User2Article;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface AccountApi {

    @FormUrlEncoded
    @POST("users/login")
    @Headers({
            "Accept: application/json",
    })
    Observable<ApiResult<LoginData>> login(@Field("loginname") String name,
                                           @Field("passwd") String passwd);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("users/register")
    Observable<ApiResult<LoginData>> register(@Field("loginname") String name,
                                        @Field("passwd") String passwd,
                                        @Field("name") String nickname);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("users/register")
    Observable<ApiResult<LoginData>> register(@FieldMap Map<String, String> infos);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("users/resetpasswd")
    Observable<ApiResult<LoginData>> resetPass(@Field("loginname") String name,
                                           @Field("passwd") String newpass);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @PUT("users/")
    Observable<ApiResult<User>> update(@FieldMap Map<String, Object> infos,
                                       @Header("x-access-token") String token);

    @GET("users/{uid}")
    Observable<ApiResult<User>> profile(@Path("uid") String uid);

    @GET("users/{uid}/reads")
    Observable<ApiResult<List<User2Article>>> reads(@Path("uid") String uid);

    @GET("users/{uid}/collects")
    Observable<ApiResult<List<User2Article>>> collects(@Path("uid") String uid);

    @GET("users/{uid}/toreads")
    Observable<ApiResult<List<User2Article>>> toreads(@Path("uid") String uid);
}

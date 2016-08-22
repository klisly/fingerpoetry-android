package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.LoginData;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

public interface AccountApi {

    @FormUrlEncoded
    @POST("users/login")
    Observable<ApiResult<LoginData>> login(@Field("loginname") String name,
                                           @Field("passwd") String passwd);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("users/register")
    Observable<ApiResult<LoginData>> register(@Field("loginname") String name,
                                        @Field("passwd") String passwd,
                                        @Field("name") String nickname);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("users/resetpasswd")
    Observable<ApiResult<LoginData>> resetPass(@Field("loginname") String name,
                                           @Field("passwd") String newpass);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @PUT("users/")
    Observable<ApiResult<LoginData>> update(@FieldMap Map<String, Object> infos,
                                            @Header("x-access-token") String token);
}

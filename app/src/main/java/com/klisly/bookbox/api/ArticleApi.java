package com.klisly.bookbox.api;

import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.LoginData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface ArticleApi {

    @FormUrlEncoded
    @POST("channels/login")
    Observable<ApiResult<LoginData>> login(@Field("loginname") String name,
                                           @Field("passwd") String passwd);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("users/register")
    Observable<ApiResult<LoginData>> register(@Field("loginname") String name,
                                              @Field("passwd") String passwd,
                                              @Field("nickname") String nickname);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("users/resetpasswd")
    Observable<ApiResult<LoginData>> resetPass(@Field("loginname") String name,
                                               @Field("passwd") String newpass);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("users/resetpasswd")
    Observable<ApiResult<LoginData>> updateProfile(@Field("loginname") String name,
                                                   @Field("passwd") String newpass);
}

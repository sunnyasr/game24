package com.mycondo.a99hub24.data.network

import com.mycondo.a99hub24.data.responses.LogoutResponse
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BaseApi {
    @FormUrlEncoded
    @POST("logout")
    fun logout(
        @Field("token") token: String,
    ): LogoutResponse
}
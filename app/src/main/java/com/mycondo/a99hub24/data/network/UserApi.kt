package com.mycondo.a99hub24.data.network

import com.mycondo.a99hub24.data.responses.LoginResponse
import com.mycondo.a99hub24.data.responses.LogoutResponse
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("user")
    suspend fun getUser(): LoginResponse

    @FormUrlEncoded
    @POST("logout")
    fun logout(
        @Field("token") token: String,
    ): LogoutResponse

}
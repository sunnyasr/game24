package com.mycondo.a99hub24.data.network

import com.mycondo.a99hub24.data.responses.LimitResponse
import com.mycondo.a99hub24.data.responses.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.*

interface HomeApi : BaseApi {

    @GET("limit")
    suspend fun getLimitCoins(@Query("token") token: String): List<LimitResponse>

    @GET("inplay/sports")
    suspend fun getInPlay(): ResponseBody
}
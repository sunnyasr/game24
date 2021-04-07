package com.mycondo.a99hub24.data.network

import com.mycondo.a99hub24.data.responses.LimitResponse
import com.mycondo.a99hub24.data.responses.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface LedgerApi {

    @GET("ledger")
    suspend fun getLedger(@Query("token") token: String): ResponseBody

}
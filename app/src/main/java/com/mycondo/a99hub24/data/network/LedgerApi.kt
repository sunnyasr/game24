package com.mycondo.a99hub24.data.network

import okhttp3.ResponseBody
import retrofit2.http.*

interface LedgerApi :BaseApi {

    @GET("ledger")
    suspend fun getLedger(@Query("token") token: String): ResponseBody

}
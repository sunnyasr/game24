package com.mycondo.a99hub24.data.repository

import com.mycondo.a99hub24.data.network.BaseApi
import com.mycondo.a99hub24.data.network.SafeApiCall

abstract class BaseRepository(private val api: BaseApi) : SafeApiCall {



    suspend fun logout(token:String) = safeApiCall {
        api.logout(token)
    }
}
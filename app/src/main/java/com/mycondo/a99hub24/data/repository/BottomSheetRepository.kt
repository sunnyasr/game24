package com.mycondo.a99hub24.data.repository

import com.mycondo.a99hub24.data.network.AuthApi
import com.mycondo.a99hub24.data.network.HomeApi
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.network.UserApi
import com.mycondo.a99hub24.data.preferences.LimitPreferences
import com.mycondo.a99hub24.data.preferences.UserPreferences
import com.mycondo.a99hub24.data.responses.LimitResponse
import com.mycondo.a99hub24.data.responses.LoginResponse

class BottomSheetRepository(
    private val api: UserApi,

) : BaseRepository() {

//    suspend fun getCoins(
//        token: String
//    ) = safeApiCall {
//        api.getLimitCoins(token)
//    }


//    suspend fun saveCoins(token: LimitResponse) {
//        preferences.store(token)
//    }

}


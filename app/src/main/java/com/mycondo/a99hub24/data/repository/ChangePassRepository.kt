package com.mycondo.a99hub24.data.repository

import com.mycondo.a99hub24.data.network.AuthApi
import com.mycondo.a99hub24.data.network.ChangePassApi
import com.mycondo.a99hub24.data.preferences.UserPreferences
import com.mycondo.a99hub24.data.responses.LoginResponse
import javax.inject.Inject

class ChangePassRepository @Inject constructor(
    private val api: ChangePassApi
) : BaseRepository(api) {

    suspend fun changePass(
        token: String,
        oldpass: String,
        newpass: String
    ) = safeApiCall {
        api.changePass(token, oldpass, newpass)
    }

}
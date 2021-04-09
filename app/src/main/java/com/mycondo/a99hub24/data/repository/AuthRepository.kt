package com.mycondo.a99hub24.data.repository

import com.mycondo.a99hub24.data.network.AuthApi
import com.mycondo.a99hub24.data.preferences.UserPreferences
import com.mycondo.a99hub24.data.responses.LoginResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository(api) {

    suspend fun login(
        email: String,
        password: String,
        ip: String
    ) = safeApiCall {
        api.login(email, password, ip)
    }

    suspend fun saveAuthToken(token: LoginResponse) {
        preferences.saveAuthToken(token)
    }

}
package com.mycondo.a99hub24.data.repository

import com.mycondo.a99hub24.data.network.LedgerApi
import com.mycondo.a99hub24.data.preferences.LimitPreferences

class LedgerRepository(
    private val api: LedgerApi
) : BaseRepository() {

    suspend fun getCoins(
        token: String
    ) = safeApiCall {
        api.getLedger(token)
    }
}


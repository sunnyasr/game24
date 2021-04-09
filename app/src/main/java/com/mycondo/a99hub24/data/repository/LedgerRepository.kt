package com.mycondo.a99hub24.data.repository

import com.mycondo.a99hub24.data.network.LedgerApi
import javax.inject.Inject

class LedgerRepository  @Inject constructor(
    private val api: LedgerApi
) : BaseRepository(api) {

    suspend fun getLedger(
        token: String
    ) = safeApiCall {
        api.getLedger(token)
    }
}


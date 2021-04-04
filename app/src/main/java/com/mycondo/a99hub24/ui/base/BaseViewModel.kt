package com.mycondo.a99hub24.ui.base

import androidx.lifecycle.ViewModel
import com.mycondo.a99hub24.data.network.UserApi
import com.mycondo.a99hub24.data.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {

    suspend fun logout(api: UserApi) = withContext(Dispatchers.IO) { repository.logout(api) }

}
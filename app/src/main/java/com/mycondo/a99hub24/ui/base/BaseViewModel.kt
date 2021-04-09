package com.mycondo.a99hub24.ui.base

import androidx.lifecycle.ViewModel
import com.mycondo.a99hub24.data.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {

    suspend fun logout(token: String) = withContext(Dispatchers.IO) { repository.logout(token) }

}
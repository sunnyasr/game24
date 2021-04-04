package com.mycondo.a99hub24.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.repository.HomeRepository
import com.mycondo.a99hub24.data.responses.LimitResponse
import com.mycondo.a99hub24.data.responses.LoginResponse
import com.mycondo.a99hub24.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel(repository) {

    private val _limitResponse: MutableLiveData<Resource<List<LimitResponse>>> = MutableLiveData()
    val limitResponse: LiveData<Resource<List<LimitResponse>>>
        get() = _limitResponse

    fun getCoins(
        token: String
    ) = viewModelScope.launch {
        _limitResponse.value = Resource.Loading
        _limitResponse.value = repository.getCoins(token)
    }
}
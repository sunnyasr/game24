package com.mycondo.a99hub24.ui.my_ledger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.repository.HomeRepository
import com.mycondo.a99hub24.data.repository.LedgerRepository
import com.mycondo.a99hub24.data.responses.LimitResponse
import com.mycondo.a99hub24.data.responses.LoginResponse
import com.mycondo.a99hub24.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class LedgerViewModel(private val repository: LedgerRepository) : BaseViewModel(repository) {


    private val _LedgerResponse: MutableLiveData<Resource<ResponseBody>> = MutableLiveData()
    val ledgerResponse: LiveData<Resource<ResponseBody>>
        get() = _LedgerResponse

    fun getCoins(
        token: String
    ) = viewModelScope.launch {
        _LedgerResponse.value = Resource.Loading
        _LedgerResponse.value = repository.getCoins(token)
    }

}
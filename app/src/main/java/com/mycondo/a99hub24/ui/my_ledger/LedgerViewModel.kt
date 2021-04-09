package com.mycondo.a99hub24.ui.my_ledger

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.repository.LedgerRepository
import com.mycondo.a99hub24.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class LedgerViewModel @ViewModelInject constructor(private val repository: LedgerRepository) :
    BaseViewModel(repository) {


    private val _LedgerResponse: MutableLiveData<Resource<ResponseBody>> = MutableLiveData()
    val ledgerResponse: LiveData<Resource<ResponseBody>>
        get() = _LedgerResponse

    fun getLedger(
        token: String
    ) = viewModelScope.launch {
        _LedgerResponse.value = Resource.Loading
        _LedgerResponse.value = repository.getLedger(token)
    }

}
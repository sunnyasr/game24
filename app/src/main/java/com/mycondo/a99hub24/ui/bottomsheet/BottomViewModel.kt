package com.mycondo.a99hub24.ui.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.repository.BottomSheetRepository
import com.mycondo.a99hub24.data.responses.LimitResponse
import com.mycondo.a99hub24.ui.base.BaseViewModel

class BottomViewModel(private val repository: BottomSheetRepository) : BaseViewModel(repository) {

    private val _limitResponse: MutableLiveData<Resource<List<LimitResponse>>> = MutableLiveData()
    val limitResponse: LiveData<Resource<List<LimitResponse>>>
        get() = _limitResponse

//    fun getCoins(
//        token: String
//    ) = viewModelScope.launch {
//        _limitResponse.value = Resource.Loading
//        _limitResponse.value = repository.getCoins(token)
//    }
}
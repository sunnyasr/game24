package com.mycondo.a99hub24.ui.change_password

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.repository.ChangePassRepository
import com.mycondo.a99hub24.data.repository.HomeRepository
import com.mycondo.a99hub24.data.responses.ChangePassResponse
import com.mycondo.a99hub24.data.responses.LimitResponse
import com.mycondo.a99hub24.model.InPlayGame
import com.mycondo.a99hub24.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class ChangePassViewModel @ViewModelInject constructor
    (private val repository: ChangePassRepository) :
    BaseViewModel(repository) {

    private val _changePassResponse: MutableLiveData<Resource<ChangePassResponse>> =
        MutableLiveData()
    val changePassResponse: LiveData<Resource<ChangePassResponse>>
        get() = _changePassResponse


    fun changePass(
        token: String,
        oldpass: String,
        newpass: String
    ) = viewModelScope.launch {
        _changePassResponse.value = Resource.Loading
        _changePassResponse.value = repository.changePass(token, oldpass, newpass)
    }
}
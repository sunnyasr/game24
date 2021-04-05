package com.mycondo.a99hub24.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycondo.a99hub24.data.repository.AuthRepository
import java.lang.IllegalArgumentException
import com.mycondo.a99hub24.data.repository.BaseRepository
import com.mycondo.a99hub24.data.repository.BottomSheetRepository
import com.mycondo.a99hub24.data.repository.HomeRepository
import com.mycondo.a99hub24.ui.auth.AuthViewModel
import com.mycondo.a99hub24.ui.bottomsheet.BottomViewModel
import com.mycondo.a99hub24.ui.home.HomeViewModel
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as HomeRepository) as T
            modelClass.isAssignableFrom(BottomViewModel::class.java) -> BottomViewModel(repository as BottomSheetRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}
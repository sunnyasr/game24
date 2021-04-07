package com.mycondo.a99hub24.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycondo.a99hub24.data.repository.*
import java.lang.IllegalArgumentException
import com.mycondo.a99hub24.ui.auth.AuthViewModel
import com.mycondo.a99hub24.ui.bottomsheet.BottomViewModel
import com.mycondo.a99hub24.ui.home.HomeViewModel
import com.mycondo.a99hub24.ui.my_ledger.LedgerViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as HomeRepository) as T
            modelClass.isAssignableFrom(BottomViewModel::class.java) -> BottomViewModel(repository as BottomSheetRepository) as T
            modelClass.isAssignableFrom(LedgerViewModel::class.java) -> LedgerViewModel(repository as LedgerRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}
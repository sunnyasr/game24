package com.mycondo.a99hub24.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.kaopiz.kprogresshud.KProgressHUD
import com.mycondo.a99hub24.data.network.RemoteDataSource
import com.mycondo.a99hub24.data.network.UserApi
import com.mycondo.a99hub24.data.preferences.LimitPreferences
import com.mycondo.a99hub24.data.preferences.UserPreferences
import com.mycondo.a99hub24.data.repository.BaseRepository
import com.mycondo.a99hub24.ui.auth.AuthActivity
import com.mycondo.a99hub24.ui.utils.startNewActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

    protected lateinit var userPreferences: UserPreferences
    protected lateinit var limitPreferences: LimitPreferences
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()
    lateinit var kProgressHUD: KProgressHUD


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences(requireContext())
        limitPreferences = LimitPreferences(requireContext())
        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
        setProgress()
        lifecycleScope.launch { userPreferences.authToken.first() }

        return binding.root
    }

    fun setProgress() {
        kProgressHUD = KProgressHUD(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
    }

    fun logout() = lifecycleScope.launch {
        val authToken = userPreferences.authToken.first()
        val api = remoteDataSource.buildApi(UserApi::class.java, authToken)
        if (authToken != null) {
            viewModel.logout(api,authToken)
        }
        userPreferences.clear()
        requireActivity().startNewActivity(AuthActivity::class.java)
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getFragmentRepository(): R

}
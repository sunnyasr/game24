package com.mycondo.a99hub24.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.mycondo.a99hub24.data.network.AuthApi
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.repository.AuthRepository
import com.mycondo.a99hub24.databinding.FragmentAuthBinding
import com.mycondo.a99hub24.ui.base.BaseFragment
import com.mycondo.a99hub24.ui.home.HomeActivity
import com.mycondo.a99hub24.ui.utils.handleApiError
import com.mycondo.a99hub24.ui.utils.startNewActivity
import kotlinx.coroutines.launch


class AuthFragment : BaseFragment<AuthViewModel, FragmentAuthBinding, AuthRepository>() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
//            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
//            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value)
                        requireActivity().startNewActivity(HomeActivity::class.java)
                        kProgressHUD.dismiss()
                    }

                }
                is Resource.Failure -> {
                    handleApiError(it) { login() }
                }

            }

        })

        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        kProgressHUD.show()
        viewModel.login(username, password, "")
    }


    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAuthBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)


}
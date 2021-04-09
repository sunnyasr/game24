package com.mycondo.a99hub24.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.kaopiz.kprogresshud.KProgressHUD
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.databinding.FragmentAuthBinding
import com.mycondo.a99hub24.ui.home.HomeActivity
import com.mycondo.a99hub24.ui.utils.handleApiError
import com.mycondo.a99hub24.ui.utils.startNewActivity
import com.sdsmdg.tastytoast.TastyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {
    private lateinit var binding: FragmentAuthBinding
    private val viewModel by viewModels<AuthViewModel>()
    private lateinit var kProgressHUD: KProgressHUD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = FragmentAuthBinding.bind(view)
        setProgress()
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {

            when (it) {

                is Resource.Loading -> {
                    kProgressHUD.show()
                }
                is Resource.Success -> {
                    kProgressHUD.dismiss()

                    lifecycleScope.launch {
                        if (it.value.status) {
                            viewModel.saveAuthToken(it.value)
                            requireActivity().startNewActivity(HomeActivity::class.java)

                        } else
                            TastyToast.makeText(
                                requireActivity(),
                                "Invalid Username/Password try again",
                                Toast.LENGTH_LONG, TastyToast.ERROR
                            )

                    }
                }

                is Resource.Failure -> {
                    kProgressHUD.dismiss()
                    handleApiError(it) { login() }
                }

            }

        })

        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    fun setProgress() {
        kProgressHUD = KProgressHUD(requireActivity())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
    }

    private fun login() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        viewModel.login(username, password, "")
    }

}
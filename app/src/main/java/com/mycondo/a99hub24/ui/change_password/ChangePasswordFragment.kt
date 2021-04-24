package com.mycondo.a99hub24.ui.change_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.preferences.UserPreferences
import com.mycondo.a99hub24.databinding.FragmentBottomSheetListDialogBinding
import com.mycondo.a99hub24.databinding.FragmentChangePasswordBinding
import com.mycondo.a99hub24.ui.home.HomeViewModel
import com.mycondo.a99hub24.ui.utils.progress
import com.mycondo.a99hub24.ui.utils.toast
import com.sdsmdg.tastytoast.TastyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class ChangePasswordFragment : DialogFragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ChangePassViewModel>()

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.changePassResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    progress(true)
                }
                is Resource.Success -> {
                    progress(false)
                    if (it.value.status) {
                        toast(it.value.msg, TastyToast.SUCCESS)
                        dismiss()
                    } else {
                        toast(it.value.msg, TastyToast.ERROR)
                    }
                }
                is Resource.Failure -> {
                    toast("Failed", TastyToast.ERROR)
                    progress(false)
                    dismiss()
                }
            }
        })

        binding.btnClose.setOnClickListener {
            dialog?.dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        binding.btnUpdate.setOnClickListener {
            changePass()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    fun changePass() {
        val oldPass = binding.etOldPass.text.toString().trim()
        val newPass = binding.etPassword.text.toString().trim()
        val newCPass = binding.etConPassword.text.toString().trim()
        val token = runBlocking { userPreferences.authToken.first() }

        if (oldPass.isEmpty())
            toast("Enter Old Password", TastyToast.INFO)
        else if (newPass.isEmpty())
            toast("Enter New Password", TastyToast.INFO)
        else if (newCPass.isEmpty())
            toast("Enter Confirm Password", TastyToast.INFO)
        else if (!newPass.equals(newCPass))
            toast("New Password And Confirm Password does not match", TastyToast.INFO)
        else
            token?.let { viewModel.changePass(it, oldPass, newPass) }
    }

}
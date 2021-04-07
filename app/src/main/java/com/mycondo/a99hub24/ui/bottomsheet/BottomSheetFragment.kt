package com.mycondo.a99hub24.ui.bottomsheet

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.adapters.BottomSheetAdapter
import com.mycondo.a99hub24.data.network.RemoteDataSource
import com.mycondo.a99hub24.data.network.UserApi
import com.mycondo.a99hub24.data.preferences.LimitPreferences
import com.mycondo.a99hub24.data.preferences.UserPreferences
import com.mycondo.a99hub24.data.repository.BottomSheetRepository
import com.mycondo.a99hub24.databinding.FragmentBottomSheetListDialogBinding
import com.mycondo.a99hub24.event_bus.BottomSheetEvent
import com.mycondo.a99hub24.ui.auth.AuthActivity
import com.mycondo.a99hub24.ui.base.ViewModelFactory
import com.mycondo.a99hub24.ui.change_password.ChangePasswordFragment
import com.mycondo.a99hub24.ui.utils.startNewActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetListDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrayList: ArrayList<BottomSheetModel>
    private lateinit var bottomSheetAdapter: BottomSheetAdapter
    private lateinit var userPreferences: UserPreferences
    protected val remoteDataSource = RemoteDataSource()
    private var navController: NavController? = null
    protected lateinit var viewModel: BottomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        _binding = FragmentBottomSheetListDialogBinding.inflate(layoutInflater, container, false)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(BottomViewModel::class.java)
//        dialog!!.window!!.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)
//        val p = dialog!!.window!!.attributes
//        p.width = ViewGroup.LayoutParams.MATCH_PARENT
//        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
//        p.x = 200
//        dialog!!.window!!.setAttributes(p)
        return binding.root
    }

    fun getFragmentRepository() =
        BottomSheetRepository(remoteDataSource.buildApi(UserApi::class.java))

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arrayList = ArrayList()
        userPreferences = UserPreferences(requireContext())

        navController = activity?.let {
            Navigation.findNavController(it, R.id.fragment2)
        }

        arrayList.add(
            BottomSheetModel(
                getString(R.string.fa_book_solid),
                getString(R.string.my_ledger)
            )
        )
        arrayList.add(
            BottomSheetModel(
                getString(R.string.fa_basketball_ball_solid),
                getString(R.string.my_commision)
            )
        )
        arrayList.add(
            BottomSheetModel(
                getString(R.string.fa_address_card_solid),
                getString(R.string.rules)
            )
        )
        arrayList.add(
            BottomSheetModel(
                getString(R.string.fa_unlock_solid),
                getString(R.string.change_password)
            )
        )
        arrayList.add(
            BottomSheetModel(
                getString(R.string.fa_sign_out_alt_solid),
                getString(R.string.logout)
            )
        )

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        bottomSheetAdapter = BottomSheetAdapter(context, arrayList)
//        recyclerView = binding.list

        binding.list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = bottomSheetAdapter
        }

    }

    fun logout() = lifecycleScope.launch {
        val authToken = userPreferences.authToken.first()
        val api = remoteDataSource.buildApi(UserApi::class.java, authToken)
        if (authToken != null) {
            viewModel.logout(api, authToken)
        }
        userPreferences.clear()
        LimitPreferences(requireContext()).clear()
        requireActivity().startNewActivity(AuthActivity::class.java)
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onBottomSheetEvent(bottomSheetEvent: BottomSheetEvent) {
//        Toast.makeText(context, bottomSheetEvent.event.toString(), Toast.LENGTH_LONG).show()
        if (bottomSheetEvent.event == 0) {
            navController?.navigate(R.id.action_bottomSheetFragment_to_ledgerFragment)
        }
        if (bottomSheetEvent.event == 2) {
            navController?.navigate(R.id.action_bottomSheetFragment_to_rulesFragment)
        }
        if (bottomSheetEvent.event == 3) {
            ChangePasswordFragment().show(requireFragmentManager(), "changepass")
            dialog?.dismiss()
        }
        if (bottomSheetEvent.event == 4) {
            logout()
        }
    }

}
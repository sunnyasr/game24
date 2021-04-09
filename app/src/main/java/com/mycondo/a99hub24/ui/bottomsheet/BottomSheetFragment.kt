package com.mycondo.a99hub24.ui.bottomsheet

import android.os.Bundle
import android.view.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.adapters.BottomSheetAdapter
import com.mycondo.a99hub24.data.preferences.UserPreferences
import com.mycondo.a99hub24.databinding.FragmentBottomSheetListDialogBinding
import com.mycondo.a99hub24.event_bus.BottomSheetEvent
import com.mycondo.a99hub24.ui.change_password.ChangePasswordFragment
import com.mycondo.a99hub24.ui.utils.logout
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetListDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrayList: ArrayList<BottomSheetModel>
    private lateinit var bottomSheetAdapter: BottomSheetAdapter
    @Inject lateinit var userPreferences: UserPreferences
    private var navController: NavController? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        _binding = FragmentBottomSheetListDialogBinding.inflate(layoutInflater, container, false)

//        dialog!!.window!!.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)
//        val p = dialog!!.window!!.attributes
//        p.width = ViewGroup.LayoutParams.MATCH_PARENT
//        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
//        p.x = 200
//        dialog!!.window!!.setAttributes(p)
        return binding.root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arrayList = ArrayList()


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

        binding.list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = bottomSheetAdapter
        }

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
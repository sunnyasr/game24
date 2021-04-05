package com.mycondo.a99hub24.ui.bottomsheet

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.adapters.BottomSheetAdapter
import com.mycondo.a99hub24.databinding.FragmentBottomSheetListDialogBinding
import com.mycondo.a99hub24.event_bus.BottomSheetEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetListDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var arrayList: ArrayList<BottomSheetModel>
    private lateinit var bottomSheetAdapter: BottomSheetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetListDialogBinding.inflate(layoutInflater, container, false)
//        return inflater.inflate(R.layout.fragment_bottom_sheet_list_dialog, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arrayList = ArrayList()

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
        Toast.makeText(context, bottomSheetEvent.event.toString(), Toast.LENGTH_LONG).show()

        if (bottomSheetEvent.event == 4) {

            Toast.makeText(context, "bottomSheetEvent.event.name", Toast.LENGTH_LONG).show()
        }
    }

}
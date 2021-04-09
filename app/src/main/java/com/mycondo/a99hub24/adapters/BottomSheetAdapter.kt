package com.mycondo.a99hub24.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.event_bus.BottomSheetEvent
import com.mycondo.a99hub24.ui.bottomsheet.BottomSheetModel
import info.androidhive.fontawesome.FontTextView
import org.greenrobot.eventbus.EventBus

import java.util.*

class BottomSheetAdapter(
    private val context: Context?,
    private var arrayList: ArrayList<BottomSheetModel>
) :
    RecyclerView.Adapter<BottomSheetAdapter.SheetViewHolder>() {

    inner class SheetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: FontTextView = itemView.findViewById(R.id.icon)
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val cardView: LinearLayout = itemView.findViewById(R.id.single_view_sheet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SheetViewHolder {

        return SheetViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_bottom_sheet_list_dialog_item, parent, false)
        )
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: SheetViewHolder, position: Int) {
        val data = arrayList[position]
        holder.icon.text = data.icon
        holder.name.text = data.name

        holder.cardView.setOnClickListener {

            EventBus.getDefault().postSticky(BottomSheetEvent(position))
        }
    }

    override fun getItemCount(): Int = arrayList.size

    fun setData(arrayList: ArrayList<BottomSheetModel>) {
        this.arrayList = arrayList
        notifyDataSetChanged()
    }
}
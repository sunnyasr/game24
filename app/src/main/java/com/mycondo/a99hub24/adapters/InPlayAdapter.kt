package com.mycondo.a99hub24.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.model.InPlayGame
import java.text.SimpleDateFormat
import java.util.*

class InPlayAdapter(private val context: Context?, private var arrayList: ArrayList<InPlayGame>) :
    RecyclerView.Adapter<InPlayAdapter.InPlayViewHolder>() {

    inner class InPlayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val team: TextView = itemView.findViewById(R.id.tv_team_name)
//        val date: TextView = itemView.findViewById(R.id.tv_time)
        val cardView: LinearLayout = itemView.findViewById(R.id.carviw_inplay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InPlayViewHolder {

        return InPlayViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.single_inplay_layout, parent, false)
        )
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: InPlayViewHolder, position: Int) {
        val game = arrayList[position]
        holder.team.text = game.long_name
//
//
//        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        val dateFormat = SimpleDateFormat("hh:mm a")
//        val date = format.parse(game.start_time)
//        val time = dateFormat.format(date).toString()
//
//        holder.date.text = StringBuilder().append(DateFormat.format("MMM", date))
//            .append(" ")
//            .append(DateFormat.format("dd", date))
//            .append(", ")
//            .append(time)
//
//        holder.cardView.setOnClickListener {
////            EventBus.getDefault().postSticky(InPLayEvent(game))
//        }


    }

    override fun getItemCount(): Int = arrayList.size

    fun setData(arrayList: ArrayList<InPlayGame>) {
        this.arrayList = arrayList
        notifyDataSetChanged()
    }
}
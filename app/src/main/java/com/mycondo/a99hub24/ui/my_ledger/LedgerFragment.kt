package com.mycondo.a99hub24.ui.my_ledger

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.a99hub.model.EventModel
import com.example.a99hub.model.MatchMarketsModel
import com.example.a99hub.model.SessionMarketsModel
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.common.Common
import com.mycondo.a99hub24.data.network.LedgerApi
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.repository.LedgerRepository
import com.mycondo.a99hub24.databinding.FragmentLedgerBinding
import com.mycondo.a99hub24.model.Ledger
import com.mycondo.a99hub24.ui.base.BaseFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import java.util.stream.Collectors
import java.text.SimpleDateFormat
import android.text.format.DateFormat

class LedgerFragment : BaseFragment<LedgerViewModel, FragmentLedgerBinding, LedgerRepository>() {


    private lateinit var eventList: ArrayList<EventModel>
    private lateinit var matchMarketList: ArrayList<MatchMarketsModel>
    private lateinit var sessionMarketList: ArrayList<SessionMarketsModel>
    private lateinit var arrayList: ArrayList<Ledger>
    private lateinit var tempList: ArrayList<Ledger>
    private lateinit var tl: TableLayout

    override fun getViewModel() = LedgerViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLedgerBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        LedgerRepository(remoteDataSource.buildApi(LedgerApi::class.java))

    @SuppressLint("NewApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arrayList = ArrayList()
        tempList = ArrayList()
        matchMarketList = ArrayList()
        eventList = ArrayList()
        sessionMarketList = ArrayList()
        tl = binding.table
        viewModel.ledgerResponse.observe(viewLifecycleOwner, {

            when (it) {
                is Resource.Success -> {
                    kProgressHUD.dismiss()
                    ledger(it.value.string())
                }
                is Resource.Loading -> {
                    kProgressHUD.show()
                }
                is Resource.Failure -> {
                    kProgressHUD.dismiss()
                    Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                }
            }
        })

        val token = runBlocking { userPreferences.authToken.first() }
        token?.let {
//            kProgressHUD.show()
            viewModel.getCoins(it)
        }
    }


    @SuppressLint("NewApi")
    fun ledger(str: String) {
        arrayList.clear()
        matchMarketList.clear()
        eventList.clear()
        sessionMarketList.clear()
        val data = JSONObject(str)
        context?.let {
            if (Common(it).checkTokenExpiry(data.toString())) {
                lifecycleScope.launch {
                    logout()
                }
            } else {
                /*EVENTS*/
                if (Common(requireActivity()).checkJSONObject(data.getString("events"))) {
                    val events: JSONObject = data.getJSONObject("events")
                    val x: Iterator<*> = events.keys()
                    val jsonEventArray = JSONArray()
                    while (x.hasNext()) {
                        val key = x.next() as String
                        jsonEventArray.put(events[key])
                    }

                    for (i in 1..jsonEventArray.length()) {
                        val jsonObject = jsonEventArray.getJSONObject(i - 1)
                        var winner: String = ""
                        if (!jsonObject.getString("winner").equals("null"))
                            winner = jsonObject.getString("winner")
                        eventList.add(
                            EventModel(
                                jsonObject.getString("event_id"),
                                jsonObject.getString("market_id"),
                                jsonObject.getString("long_name"),
                                jsonObject.getString("short_name"),
                                winner,
                                jsonObject.getString("start_time"),
                            )
                        )
                    }
                }

                /*SESSION MARKET*/
                if (Common(requireContext()).checkJSONObject(data.getString("session_markets"))) {
                    val session_markets: JSONObject =
                        data.getJSONObject("session_markets")
                    val _x_session: Iterator<*> = session_markets.keys()
                    val jsonSessionArray = JSONArray()
                    while (_x_session.hasNext()) {
                        val key = _x_session.next() as String
                        jsonSessionArray.put(session_markets[key])
                    }
                    for (j in 1..jsonSessionArray.length()) {
                        val sessionObject = jsonSessionArray.getJSONObject(j - 1)
                        sessionMarketList.add(
                            SessionMarketsModel(
                                sessionObject.getString("event_id"),
                                sessionObject.getString("transaction"),
                                sessionObject.getString("commission")
                            )
                        )
                    }
                }

                /*MATCH MARKET*/
                if (Common(requireContext()).checkJSONObject(data.getString("match_markets"))) {
                    val match_markets: JSONObject = data.getJSONObject("match_markets")
                    val _x_match: Iterator<*> = match_markets.keys()
                    val jsonMatchArray = JSONArray()
                    while (_x_match.hasNext()) {
                        val key = _x_match.next() as String
                        jsonMatchArray.put(match_markets[key])
                    }
                    for (i in 1..jsonMatchArray.length()) {
                        val jsonObject = jsonMatchArray.getJSONObject(i - 1)

                        matchMarketList.add(
                            MatchMarketsModel(
                                jsonObject.getString("event_id"),
                                jsonObject.getString("market_id"),
                                jsonObject.getString("transaction"),
                                jsonObject.getString("commission"),
                            )
                        )
                    }
                }

                /*Settlement*/
                val jsonSettlementArray = data.getJSONArray("settlement_list")
                for (s in 1..jsonSettlementArray.length()) {
                    val settelment = jsonSettlementArray.getJSONObject(s - 1)
                    var lost: String = "0"
                    var won: String = "0"
                    if (settelment.getString("type").equals("1")) {
                        won = settelment.getString("amount").replace("-", "").toDouble()
                            .toInt()
                            .toString()
                    } else
                        lost =
                            settelment.getString("amount").replace("-", "").toDouble()
                                .toInt()
                                .toString()

                    arrayList.add(
                        Ledger(
                            "0",
                            "0",
                            "settlement " + settelment.getString("remark"),
                            "settlement " + settelment.getString("remark"),
                            "",
                            settelment.getString("created"),
                            settelment.getString("amount").replace("-", ""),

                            lost,
                            won,
                            settelment.getString("amount").replace("-", "")
                        )
                    )
                }

                /*Final Ledger*/
                for (i in 1..eventList.size) {
                    val jsonObject = eventList.get(i - 1)

                    val smarket = sessionMarketList.stream().filter {
                        it.getEventID().contains(jsonObject.event_id)

                    }.collect(Collectors.toList())
                    val mmarket = matchMarketList.stream().filter {
                        it.getEventID().contains(jsonObject.event_id)

                    }.collect(Collectors.toList())
                    var winner: String = jsonObject.winner
                    val names = jsonObject.long_name.split('v')
                    for (name in names) {

                        if (name.replace(" ", "").take(1).equals(winner.take(1))) {
                            winner = name
                        }
                    }
                    var lost: String = "0"
                    var won: String = "0"
                    if (smarket.size != 0)
                        if (smarket.get(0).getTransaction().toDouble() > 0) {
                            won =
                                smarket.get(0).getTransaction().replace("-", "")
                                    .toDouble()
                                    .toInt().toString()
                        } else {
                            lost =
                                smarket.get(0).getTransaction().replace("-", "")
                                    .toDouble()
                                    .toInt().toString()
                        }
                    if (mmarket.size != 0) {
                        if (mmarket.get(0).getTransaction().toDouble() > 0) {
                            if (won.toInt() > 0)
                                won = (won.toDouble() + mmarket.get(0).getTransaction()
                                    .replace("-", "")
                                    .toDouble()).toInt().toString()
                            else {
                                val temp = (mmarket.get(0).getTransaction()
                                    .replace("-", "")
                                    .toDouble() - lost.toDouble()).toInt()
                                if (temp > 0) {
                                    won = temp.toString().replace("-", "")
                                    lost = "0"
                                } else {
                                    lost = temp.toString().replace("-", "")
                                    won = "0"
                                }
                            }
                        } else {
                            if (won.toInt() > 0) {

                                won = (won.toDouble() - mmarket.get(0).getTransaction()
                                    .replace("-", "")
                                    .toDouble()).toInt().toString()
                                if (won > lost) {
                                    lost = "0"
                                } else {
                                    won = "0"
                                }
                            } else {
                                lost =
                                    (lost.toDouble() + mmarket.get(0).getTransaction()
                                        .replace("-", "")
                                        .toDouble()).toInt().toString()
                            }
                        }

                    }

                    arrayList.add(
                        Ledger(
                            jsonObject.event_id,
                            jsonObject.market_id,
                            jsonObject.long_name,
                            jsonObject.short_name,
                            winner,
                            jsonObject.start_time,
                            (won.toDouble().toInt() + lost.toDouble()
                                .toInt()).toString(),
                            lost,
                            won,
                            (won.toDouble().toInt() + lost.toDouble()
                                .toInt()).toString(),
                        )
                    )
                }

                /*Final List date sort*/
                arrayList.sortBy {
                    it.start_time
                }

//                ledgerViewModel.allDelete(requireContext())
//
//                ledgerViewModel.insert(requireActivity(), arrayList)
                tl.removeAllViews()

                if (arrayList.size > 0) {
                    addHeaders()
                    addData()
//                                binding.tvEmpty.visibility = GONE
                } else {
//                                binding.tvEmpty.visibility = VISIBLE
                }

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        _binding = null
        tl.removeAllViews()
    }

    private fun addHeaders() {

        context?.let {
            val tr = TableRow(it)
            val headBg = "#ffbd15"
            val headTxt = Color.BLACK
            tr.layoutParams = Common(requireActivity()).getLayoutParams()
            tr.addView(
                Common(requireContext()).getTextView(
                    0,
                    "DATE",
                    headTxt,
                    Typeface.NORMAL,
                    Color.parseColor(headBg),
                    0, 12f, 0, Gravity.CENTER, -1
                )
            )
            tr.addView(
                Common(requireContext()).getTextView(
                    0,
                    "CREDIT",
                    headTxt,
                    Typeface.NORMAL,
                    Color.parseColor(headBg),
                    0, 12f, 0, Gravity.CENTER, -1
                )
            )
            tr.addView(
                Common(requireContext()).getTextView(
                    0,
                    "DEBIT",
                    headTxt,
                    Typeface.NORMAL,
                    Color.parseColor(headBg),
                    0, 12f, 0, Gravity.CENTER, -1
                )
            )

            tr.addView(
                Common(requireContext()).getTextView(
                    0,
                    "BALANCE",
                    headTxt,
                    Typeface.NORMAL,
                    Color.parseColor(headBg),
                    0, 12f, 0, Gravity.CENTER, -1
                )
            )
            tr.addView(
                Common(requireContext()).getTextView(
                    0,
                    "WINNER/Remark",
                    headTxt,
                    Typeface.NORMAL,
                    Color.parseColor(headBg),
                    0, 12f, 0, Gravity.CENTER, -1
                )
            )
            tl.addView(tr, Common(requireContext()).getTblLayoutParams())
        }

    }

    @SuppressLint("Range")
    fun addData() {

        context?.let {
            var blc: Number
            blc = 0
            for (i in 1..arrayList.size) {
                blc += arrayList.get(i - 1).won.toDouble().toInt()
                blc -= arrayList.get(i - 1).lost.toDouble().toInt()

                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val dateFormat = SimpleDateFormat("hh:mm a")
                val date = format.parse(arrayList.get(i - 1).start_time)
                val time = dateFormat.format(date).toString()

                val dtime = StringBuilder().append(DateFormat.format("MMM", date))
                    .append(" ")
                    .append(DateFormat.format("dd", date))
                    .append(", ")
                    .append(time)


                var typeface = Typeface.NORMAL
                if (arrayList.get(i - 1).event_id.equals("0"))
                    typeface = Typeface.BOLD
                var bgColor = ""
                bgColor =
                    if (i % 2 == 0) {
                        "#FFFFFF"
                    } else "#FFFFFF"
                val tr = TableRow(context)

                tr.orientation = TableRow.VERTICAL
                tr.addView(
                    Common(requireContext()).getTextView(
                        i,
                        dtime.toString(),
                        Color.DKGRAY,
                        typeface,
                        Color.parseColor(bgColor),
                        R.drawable.profile_info_bg_style,
                        12f,
                        0,
                        Gravity.LEFT, i + 99
                    )
                )
                tr.addView(
                    Common(requireContext()).getTextView(
                        i,
                        arrayList.get(i - 1).won,
                        if (arrayList.get(i - 1).won.toInt() > 0) Color.parseColor("#2E7D32") else Color.BLACK,
                        Typeface.NORMAL,
                        Color.parseColor("#FF471A"),
                        R.drawable.profile_info_bg_style,
                        12f,
                        0,
                        Gravity.RIGHT, -1
                    )
                )
                tr.addView(
                    Common(requireContext()).getTextView(
                        i,
                        if (arrayList.get(i - 1).lost.toInt() > 0) StringBuilder().append("-")
                            .append(arrayList.get(i - 1).lost)
                            .toString() else StringBuilder()
                            .append(arrayList.get(i - 1).lost)
                            .toString(),
                        if (arrayList.get(i - 1).lost.toInt() > 0) Color.RED else Color.BLACK,
                        Typeface.NORMAL,
                        Color.parseColor(bgColor),
                        R.drawable.profile_info_bg_style,
                        12f,
                        0,
                        Gravity.RIGHT, -1
                    )
                )


                var colorBalance: Int

                if (blc > 0) {
                    colorBalance = Color.parseColor("#2E7D32")
                } else
                    colorBalance = Color.RED


                tr.addView(
                    Common(requireContext()).getTextView(
                        i,
                        blc.toString(),
                        colorBalance,
                        Typeface.NORMAL,
                        Color.parseColor(bgColor),
                        R.drawable.profile_info_bg_style,
                        12f,
                        0,
                        Gravity.RIGHT, -1
                    )
                )
                tr.addView(
                    Common(requireContext()).getTextView(
                        i,
                        arrayList.get(i - 1).long_name,
                        Color.BLACK,
                        typeface,
                        Color.parseColor(bgColor),
                        R.drawable.profile_info_bg_style,
                        12f,
                        0,
                        Gravity.LEFT, -1
                    )
                )
                tl.addView(tr, Common(requireContext()).getTblLayoutParams())
            }

        }
    }

}
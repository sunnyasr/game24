package com.mycondo.a99hub24.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.adapters.InPlayAdapter
import com.mycondo.a99hub24.adapters.MainSliderAdapter

import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.databinding.FragmentHomeBinding
import com.mycondo.a99hub24.model.InPlayGame
import com.mycondo.a99hub24.services.PicassoImageLoadingService
import com.mycondo.a99hub24.ui.utils.checkJSONObject
import com.mycondo.a99hub24.ui.utils.logout
import com.mycondo.a99hub24.ui.utils.progress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import ss.com.bannerslider.Slider

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home),
    TabLayout.OnTabSelectedListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()


    private lateinit var slider: Slider
    private lateinit var tabLayout: TabLayout
    private lateinit var arrayList: ArrayList<InPlayGame>
    private lateinit var inPLayAdapter: InPlayAdapter

    private val imageResId = intArrayOf(
        R.drawable.ic_cricket,
        R.drawable.ic_football,
        R.drawable.ic_tennis,
        R.drawable.ic_teenpatti
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        Slider.init(PicassoImageLoadingService(requireContext()))
        slider = binding.bannerSlider1
        slider.setAdapter(MainSliderAdapter())
        arrayList = ArrayList()
        inPLayAdapter = InPlayAdapter(requireContext(), arrayList)
        tabLayout = binding.tab
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.cricket)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.football)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tennis)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.teenpatti)))
        for (i in 0 until tabLayout.getTabCount()) {
            tabLayout.getTabAt(i)?.setIcon(imageResId[i])
        }
        tabLayout.addOnTabSelectedListener(this)

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = inPLayAdapter
        }
        context?.let {
            viewModel.getInPlayGame(it)
                ?.observe(requireActivity(), Observer {
                    if (it.size > 0) {
                        progress(false)
                        inPLayAdapter.setData(it as ArrayList<InPlayGame>)
                    }
                })
        }

        viewModel.inPlayResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        progress(false)
                        inplayParse(it.value.string())
                    }
                }
                is Resource.Loading -> {
                    progress(true)
                }
                is Resource.Failure -> {
                    logout()
                }
            }
        })

        progress(true)

        viewModel.getInPlay()

    }

    fun inplayParse(str: String) {
        val data = JSONObject(str)
        if (checkJSONObject(data.toString())) {

            val events: JSONObject = data.getJSONObject("events")
            val x: Iterator<*> = events.keys()
            val jsonArray = JSONArray()
            while (x.hasNext()) {
                val key = x.next() as String
                jsonArray.put(events[key])
            }

            for (i in 1..jsonArray.length()) {

                val jsonObject = jsonArray.getJSONObject(i - 1)
                val ugModel = InPlayGame(
                    jsonObject.getString("sport_id"),
                    jsonObject.getString("sport_name"),
                    jsonObject.getString("event_id"),
                    jsonObject.getString("market_id"),
                    jsonObject.getString("long_name"),
                    jsonObject.getString("short_name"),
                    jsonObject.getString("start_time"),
                    jsonObject.getString("inactive")
                )

                arrayList.add(ugModel)
            }
            viewModel.allDelete(requireActivity())

            viewModel.insert(requireActivity(), arrayList)
            inPLayAdapter.setData(arrayList)

        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
        }
    }


    override fun onTabSelected(tab: TabLayout.Tab?) {
        Toast.makeText(context, "selected " + tab?.position.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
//        Toast.makeText(context, tab?.position.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
//        Toast.makeText(context, tab?.position.toString(), Toast.LENGTH_LONG).show()
    }

}
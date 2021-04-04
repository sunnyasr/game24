package com.mycondo.a99hub24.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.adapters.MainSliderAdapter
import com.mycondo.a99hub24.data.network.HomeApi
import com.mycondo.a99hub24.data.repository.HomeRepository
import com.mycondo.a99hub24.databinding.FragmentHomeBinding
import com.mycondo.a99hub24.services.PicassoImageLoadingService
import com.mycondo.a99hub24.ui.base.BaseFragment
import ss.com.bannerslider.Slider


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, HomeRepository>(),
    TabLayout.OnTabSelectedListener {


    private lateinit var slider: Slider
    private lateinit var tabLayout: TabLayout

    private val imageResId = intArrayOf(
        R.drawable.ic_cricket,
        R.drawable.ic_football,
        R.drawable.ic_tennis,
        R.drawable.ic_teenpatti
    )

    override fun getViewModel() = HomeViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Slider.init(PicassoImageLoadingService(requireContext()))
        slider = binding.bannerSlider1
        slider.setAdapter(MainSliderAdapter())

        tabLayout = binding.tab
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.cricket)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.football)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tennis)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.teenpatti)))
        for (i in 0 until tabLayout.getTabCount()) {
            tabLayout.getTabAt(i)?.setIcon(imageResId[i])
        }

        tabLayout.addOnTabSelectedListener(this)

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        HomeRepository(remoteDataSource.buildApi(HomeApi::class.java), limitPreferences)

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Toast.makeText(context, "selected "+tab?.position.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
//        Toast.makeText(context, tab?.position.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
//        Toast.makeText(context, tab?.position.toString(), Toast.LENGTH_LONG).show()
    }

}
package com.mycondo.a99hub24.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.adapters.MainSliderAdapter
import com.mycondo.a99hub24.data.network.HomeApi
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.repository.HomeRepository
import com.mycondo.a99hub24.databinding.FragmentHomeBinding
import com.mycondo.a99hub24.services.PicassoImageLoadingService
import com.mycondo.a99hub24.ui.base.BaseFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
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


        viewModel.limitResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Success -> {
                    kProgressHUD.dismiss()
                    Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    kProgressHUD.dismiss()
//                    binding.progressbar.visible(true)
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }
        })
        val token = runBlocking { userPreferences.authToken.first() }
        token?.let {
            kProgressHUD.show()
            viewModel.getCoins(it)
        }

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        HomeRepository(remoteDataSource.buildApi(HomeApi::class.java), limitPreferences)

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
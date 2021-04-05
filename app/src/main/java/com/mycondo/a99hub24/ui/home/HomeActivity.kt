package com.mycondo.a99hub24.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.data.network.HomeApi
import com.mycondo.a99hub24.data.network.RemoteDataSource
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.preferences.LimitPreferences
import com.mycondo.a99hub24.data.preferences.UserPreferences
import com.mycondo.a99hub24.data.repository.HomeRepository
import com.mycondo.a99hub24.databinding.ActivityHomeBinding
import com.mycondo.a99hub24.ui.base.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding


    val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment2) as NavHostFragment).navController
    }

    private lateinit var limitPreferences: LimitPreferences

    private lateinit var viewModel: HomeViewModel

    protected val remoteDataSource = RemoteDataSource()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        limitPreferences = LimitPreferences(this)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)

        //Setting the navigation controller to Bottom Nav
        binding.bottomNavigation.setupWithNavController(navController)



        limitPreferences.coin.asLiveData().observe(this, {
//            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })


        viewModel.limitResponse.observe(this, Observer {

            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        limitPreferences.store(it.value.get(0))
                    }
                }
                is Resource.Loading -> {
//                    kProgressHUD.show()
//                    binding.progressbar.visible(true)
//                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
//                    logout()
                }
            }
        })
        val token = runBlocking { UserPreferences(this@HomeActivity).authToken.first() }
        token?.let {
//            kProgressHUD.show()
            viewModel.getCoins(it)
        }

    }

    fun getFragmentRepository() =
        HomeRepository(remoteDataSource.buildApi(HomeApi::class.java), limitPreferences)

    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
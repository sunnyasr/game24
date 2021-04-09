package com.mycondo.a99hub24.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kaopiz.kprogresshud.KProgressHUD
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.data.preferences.LimitPreferences
import com.mycondo.a99hub24.data.preferences.UserPreferences

import com.mycondo.a99hub24.databinding.ActivityHomeBinding
import com.mycondo.a99hub24.ui.auth.AuthActivity
import com.mycondo.a99hub24.ui.my_ledger.LedgerViewModel
import com.mycondo.a99hub24.ui.utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityHomeBinding


    val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment2) as NavHostFragment).navController
    }

    @Inject
    lateinit var limitPreferences: LimitPreferences
    @Inject
    lateinit var userPreferences: UserPreferences

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var kProgressHUD: KProgressHUD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProgress()

        //Setting the navigation controller to Bottom Nav
        binding.bottomNavigation.setupWithNavController(navController)

        limitPreferences.coin.asLiveData().observe(this, {
            binding.tvCoins.text = it.toDouble().toInt().toString()
            binding.tvExpcoins.text = StringBuilder().append("Exp : ").append(it.toDouble().toInt())
        })
        userPreferences.username.asLiveData().observe(this, {
            binding.bottomNavigation.menu.getItem(3).title =
                StringBuilder().append("(").append(it).append(")")
        })


        viewModel.limitResponse.observe(this, Observer {

            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        limitPreferences.store(it.value.get(0))
                    }
                }
                is Resource.Loading -> {
                    kProgressHUD.show()

                }
                is Resource.Failure -> {
//                    logout()
                }
            }
        })
        val token = runBlocking { UserPreferences(this@HomeActivity).authToken.first() }
        token?.let {
            viewModel.getCoins(it)
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)

    }

    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    fun performLogout() = lifecycleScope.launch {

        viewModel.logout(userPreferences.authToken.first()!!)
        userPreferences.clear()
        limitPreferences.clear()
        startNewActivity(AuthActivity::class.java)
    }

    fun token() = runBlocking { userPreferences.authToken.first() }

    fun setProgress() {
        kProgressHUD = KProgressHUD(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
    }

    fun progress(yes: Boolean) {
        if (yes) kProgressHUD.show() else kProgressHUD.dismiss()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.homeFragment) {
            navController.navigate(R.id.homeFragment)
        }
        if (item.itemId == R.id.comingFragment) {
            navController.navigate(R.id.comingFragment)
        }
        if (item.itemId == R.id.profitLossFragment) {
            navController.navigate(R.id.profitLossFragment)
        }
        if (item.itemId == R.id.bottomSheetFragment) {
            navController.navigate(R.id.bottomSheetFragment)
        }
        return true
    }
}
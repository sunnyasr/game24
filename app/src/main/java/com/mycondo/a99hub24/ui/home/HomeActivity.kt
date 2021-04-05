package com.mycondo.a99hub24.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mycondo.a99hub24.R
import com.mycondo.a99hub24.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

//    private lateinit var navController: NavController

    val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment2) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        navController = Navigation.findNavController(this, R.id.fragment2)

        //Setting the navigation controller to Bottom Nav
        binding.bottomNavigation.setupWithNavController(navController)


        //Setting up the action bar
//        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
package com.mycondo.a99hub24

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.mycondo.a99hub24.data.preferences.UserPreferences
import com.mycondo.a99hub24.ui.auth.AuthActivity
import com.mycondo.a99hub24.ui.home.HomeActivity
import com.mycondo.a99hub24.ui.utils.startNewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userPreference = UserPreferences(this)
        userPreference.authToken.asLiveData().observe(this, {
            val activit = if (it == null) AuthActivity::class.java else HomeActivity::class.java
            startNewActivity(activit)
        })
    }
}
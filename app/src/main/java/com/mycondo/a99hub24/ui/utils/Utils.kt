package com.mycondo.a99hub24.ui.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.mycondo.a99hub24.data.network.Resource
import com.mycondo.a99hub24.ui.auth.AuthFragment
import com.mycondo.a99hub24.ui.home.HomeActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}


fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.logout() = lifecycleScope.launch {
    if (activity is HomeActivity) {
        (activity as HomeActivity).performLogout()
    }
}

fun Fragment.progress(yes: Boolean) {
    if (activity is HomeActivity) {
        (activity as HomeActivity).progress(yes)
    }
}

fun Fragment.token() = runBlocking {
    if (activity is HomeActivity) {
        (activity as HomeActivity).token()
    }
}


fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
            "Please check your internet connection",
            retry
        )
        failure.errorCode == 401 -> {
            if (this is AuthFragment) {
                requireView().snackbar("You've entered incorrect username or password")
            } else {
                logout()
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()

            requireView().snackbar(error)
        }
    }
}


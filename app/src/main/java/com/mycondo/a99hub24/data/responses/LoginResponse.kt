package com.mycondo.a99hub24.data.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginResponse(
    val token: String,
    val status: Boolean,
    val client_id: String,
    val username: String,
)
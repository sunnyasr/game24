package com.mycondo.a99hub24.data.responses

import androidx.annotation.Keep

@Keep
data class LimitResponse(
    val current: String,
    val locked: String,
    val hide_commission: String,
    val new: String,
    val username: String,
    val name: String,
    val valid: String,
)
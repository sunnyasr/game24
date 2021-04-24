package com.mycondo.a99hub24.data.responses

import androidx.annotation.Keep

@Keep
data class ChangePassResponse(
    val status: Boolean,
    val msg: String,
)
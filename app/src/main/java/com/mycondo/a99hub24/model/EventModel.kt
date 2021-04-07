package com.example.a99hub.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class EventModel(
     val event_id: String,
     val market_id: String,
     val long_name: String,
     val short_name: String,
     val winner: String,
     val start_time: String,

    ) : Serializable


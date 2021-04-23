package com.example.mylib.openapi.smartdoorapi.data

import java.util.*
import java.io.Serializable

data class MqttDataItem(
    val id: Int,
    val msg: String,
    val timestamp: Date,
    val topic: String
) : Serializable
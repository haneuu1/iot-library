package com.example.mylib.openapi.smartdoorapi.data

import java.io.Serializable
import java.util.*

data class RecordingDataItem(
    val id: Int,
    val video_root: String,
    val video_timestamp: Date
) : Serializable
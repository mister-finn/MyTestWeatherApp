package com.misterfinn.mytestweatherapp.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod")
    @Expose
    val pod: String? = null
)

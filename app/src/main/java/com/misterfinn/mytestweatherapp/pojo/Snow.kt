package com.misterfinn.mytestweatherapp.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Snow(
    @SerializedName("3h")
    @Expose
    val snowfall: Double? = null
)

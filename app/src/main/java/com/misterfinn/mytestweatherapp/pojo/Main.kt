package com.misterfinn.mytestweatherapp.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    @Expose
    val temp: Double? = null,
    @SerializedName("pressure")
    @Expose
    val pressure: Int? = null,
    @SerializedName("humidity")
    @Expose
    val humidity: Int? = null
)

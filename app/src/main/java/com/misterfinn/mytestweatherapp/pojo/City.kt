package com.misterfinn.mytestweatherapp.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("country")
    @Expose
    val country: String? = null
)

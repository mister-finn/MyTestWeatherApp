package com.misterfinn.mytestweatherapp.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    @Expose
    val rainfall: Double? = null
)

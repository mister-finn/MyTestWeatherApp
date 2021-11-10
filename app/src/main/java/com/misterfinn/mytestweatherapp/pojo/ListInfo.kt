package com.misterfinn.mytestweatherapp.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListInfo(
    @SerializedName("dt")
    @Expose
    val dt: Int? = null,
    @SerializedName("main")
    @Expose
    val main: Main? = null,
    @SerializedName("weather")
    @Expose
    val weatherList: List<Weather>? = null,
    @SerializedName("clouds")
    @Expose
    val clouds: Clouds? = null,
    @SerializedName("wind")
    @Expose
    val wind: Wind? = null,
    @SerializedName("visibility")
    @Expose
    val visibility: Int? = null,
    @SerializedName("pop")
    @Expose
    val pop: Double? = null,
    @SerializedName("sys")
    @Expose
    val sys: Sys? = null,
    @SerializedName("dt_txt")
    @Expose
    val dtTxt: String? = null,
    @SerializedName("rain")
    @Expose
    val rain: Rain? = null,
    @SerializedName("snow")
    @Expose
    val snow:Snow? = null
)

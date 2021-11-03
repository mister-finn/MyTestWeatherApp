package com.misterfinn.mytestweatherapp.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Responce(
    @SerializedName("cod")
    @Expose
    val cod: String? = null,
    @SerializedName("message")
    @Expose
    val message: Int? = null,
    @SerializedName("cnt")
    @Expose
    val cnt: Int? = null,
    @SerializedName("list")
    @Expose
    val list: List<ListInfo>? = null,
    @SerializedName("city")
    @Expose
    val city: City? = null

)

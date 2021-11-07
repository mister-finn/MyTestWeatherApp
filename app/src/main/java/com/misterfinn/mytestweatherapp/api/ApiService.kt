package com.misterfinn.mytestweatherapp.api

import com.misterfinn.mytestweatherapp.pojo.Responce
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("forecast?&appid=96556e740654403c57e3457c97728f33&units=metric")
    fun loadData(@Query("lat")
                 lat:Double,
                 @Query("lon")
                 lon:Double):Single<Responce>
}
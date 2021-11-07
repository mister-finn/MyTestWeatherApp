package com.misterfinn.mytestweatherapp.api

import com.misterfinn.mytestweatherapp.pojo.Responce
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {
    @GET("forecast?lat=55.536680&lon=28.633424&appid=96556e740654403c57e3457c97728f33&units=metric")
    fun loadData():Single<Responce>
}
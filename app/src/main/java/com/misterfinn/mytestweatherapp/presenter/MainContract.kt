package com.misterfinn.mytestweatherapp.presenter

import com.misterfinn.mytestweatherapp.pojo.ForecastItem
import com.misterfinn.mytestweatherapp.pojo.TodayWeather

interface MainContract {

    interface TodayWeatherView {
        fun showTodayWeather(todayWeather: TodayWeather)
        fun showToast()
    }

    interface ForecastView {
        fun showForecast(list: ArrayList<ForecastItem>)
        fun showToast()
    }

    interface Presenter {
        fun setLocationData(lat: Double, long: Double)
        fun onDestroy()
    }

}
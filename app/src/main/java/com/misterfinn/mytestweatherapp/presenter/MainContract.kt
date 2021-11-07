package com.misterfinn.mytestweatherapp.presenter

import com.misterfinn.mytestweatherapp.pojo.ForecastItem
import com.misterfinn.mytestweatherapp.pojo.ListInfo
import com.misterfinn.mytestweatherapp.pojo.TodayWeather

interface MainContract {

    interface TodayWeatherView {
        fun showTodayWeather(todayWeather: TodayWeather)
    }

    interface ForecastView {
        fun showForecast(list: ArrayList<ForecastItem>)
    }

    interface Presenter {
        fun onDestroy()
    }

}
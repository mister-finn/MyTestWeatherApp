package com.misterfinn.mytestweatherapp.presenter

import android.util.Log
import com.misterfinn.mytestweatherapp.api.ApiFactory
import com.misterfinn.mytestweatherapp.pojo.City
import com.misterfinn.mytestweatherapp.pojo.Main
import com.misterfinn.mytestweatherapp.pojo.TodayWeather
import com.misterfinn.mytestweatherapp.pojo.Weather
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TodayWeatherPresenter(_mView: MainContract.TodayWeatherView) : MainContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private val mView: MainContract.TodayWeatherView = _mView


    private fun loadTodayWeather(lat: Double, lon: Double) {
        val data = ApiFactory.apiService.loadData(lat, lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val todayWeather = TodayWeather()
                val weather: Weather? = it.list?.get(0)?.weatherList?.get(0)
                todayWeather.imageId = weather?.id ?: 200
                todayWeather.weatherDescription = weather?.description
                val main: Main? = it.list?.get(0)?.main
                val temperature = main?.temp?.toInt().toString() + " ℃"
                val pressure = main?.pressure.toString() + " hPa"
                val humidity = main?.humidity.toString() + " %"
                todayWeather.temperature = temperature
                todayWeather.pressure = pressure
                todayWeather.humidity = humidity
                val city: City? = it.city
                val cityAndCountry = "${city?.name}, ${city?.country}"
                todayWeather.cityAndCountry = cityAndCountry
                val rainFall = it.list?.get(0)?.rain?.rainfall.toString()
                todayWeather.rainfall = "$rainFall mm"
                val windSpeed = it.list?.get(0)?.wind?.speed.toString()
                todayWeather.windSpeed = "$windSpeed m/s"
                val windDirection = when (it.list?.get(0)?.wind?.deg) {
                    in 0..15 -> "N"
                    in 16..75 -> "NE"
                    in 75..105 -> "E"
                    in 106..165 -> "SE"
                    in 166..195 -> "S"
                    in 196..255 -> "SW"
                    in 256..285 -> "W"
                    in 286..345 -> "NW"
                    in 346..359 -> "N"
                    else -> "No data"
                }
                todayWeather.windDirection = windDirection
                val isDay = it.list?.get(0)?.sys?.pod
                todayWeather.isDay = isDay == "d"
                mView.showTodayWeather(todayWeather)

            }, {
                val noDataToday = TodayWeather()
                val noData = "No data"
                with(noDataToday) {
                    imageId = 200
                    cityAndCountry = noData
                    temperature = noData
                    weatherDescription = noData
                    humidity = noData
                    rainfall = noData
                    pressure = noData
                    windSpeed = noData
                    windDirection = noData
                }
                mView.showToast()
                mView.showTodayWeather(noDataToday)
            })
        compositeDisposable.add(data)
    }

    override fun setLocationData(lat: Double, long: Double) {
        loadTodayWeather(lat, long)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}
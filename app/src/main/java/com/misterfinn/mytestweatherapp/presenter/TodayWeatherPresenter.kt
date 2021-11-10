package com.misterfinn.mytestweatherapp.presenter

import com.misterfinn.mytestweatherapp.api.ApiFactory
import com.misterfinn.mytestweatherapp.pojo.*
import com.misterfinn.mytestweatherapp.utils.makeFirstCharUpper
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
                val todayWeather = createTodayWeather(it)
                mView.showTodayWeather(todayWeather)
                mView.removeProgressBar()
            }, {
                val noDataToday = createNoDataToday()
                mView.showToast()
                mView.showTodayWeather(noDataToday)
                mView.removeProgressBar()
            })
        compositeDisposable.add(data)
    }

    override fun setLocationData(lat: Double, long: Double) {
        loadTodayWeather(lat, long)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    private fun createTodayWeather(responce: Responce): TodayWeather {
        val todayWeather = TodayWeather()
        val weather: Weather? = responce.list?.get(0)?.weatherList?.get(0)
        val main: Main? = responce.list?.get(0)?.main
        val temperature = main?.temp?.toInt().toString() + " ℃"
        val pressure = main?.pressure.toString() + " hPa"
        val humidity = main?.humidity.toString() + " %"
        val city: City? = responce.city
        val cityAndCountry = "${city?.name}, ${city?.country}"
        val rainFall = getFalls(responce.list?.get(0))
        val windSpeed = responce.list?.get(0)?.wind?.speed.toString()
        val isDay = responce.list?.get(0)?.sys?.pod

        todayWeather.imageId = weather?.id ?: 200
        todayWeather.weatherDescription = weather?.description?.makeFirstCharUpper()
        todayWeather.temperature = temperature
        todayWeather.pressure = pressure
        todayWeather.humidity = humidity
        todayWeather.cityAndCountry = cityAndCountry
        todayWeather.rainfall = rainFall
        todayWeather.windSpeed = "$windSpeed m/s"
        todayWeather.windDirection = getWindDirection(responce.list?.get(0)?.wind?.deg)
        todayWeather.isDay = isDay == "d"
        return todayWeather
    }

    private fun getFalls(listInfo: ListInfo?): String {
        val rain = listInfo?.rain
        val snow = listInfo?.snow
        if (rain != null) {
            return rain.rainfall.toString() + "mm"
        } else {
            if (snow != null) {
                return snow.snowfall.toString() + "mm"
            }
        }
        return "NaN"
    }

    private fun getWindDirection(degree: Int?): String {
        return when (degree) {
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
    }

    private fun createNoDataToday(): TodayWeather {
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
        return noDataToday
    }
}
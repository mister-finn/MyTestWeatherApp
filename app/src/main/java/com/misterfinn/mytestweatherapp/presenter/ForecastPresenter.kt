package com.misterfinn.mytestweatherapp.presenter

import android.util.Log
import com.misterfinn.mytestweatherapp.api.ApiFactory
import com.misterfinn.mytestweatherapp.pojo.ForecastItem
import com.misterfinn.mytestweatherapp.pojo.Responce
import com.misterfinn.mytestweatherapp.utils.getDayOfWeek
import com.misterfinn.mytestweatherapp.utils.getTime
import com.misterfinn.mytestweatherapp.utils.makeFirstCharUpper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ForecastPresenter(_mView: MainContract.ForecastView) :
    MainContract.Presenter {
    private val compositeDisposable = CompositeDisposable()
    private val mView: MainContract.ForecastView = _mView
    private var latitude = 0.0
    private var longitude = 0.0

    private fun loadForecast(lat: Double, lon: Double) {
        val data = ApiFactory.apiService.loadData(lat, lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val forecastList = createForecastList(it)
                val city = it.city?.name
                mView.showForecast(forecastList)
                mView.showCity(city)
                mView.removeProgressBar()
            }, {
                val list = createForecastWithNoData()
                val noData = "No data"
                mView.showCity(noData)
                mView.showForecast(list)
                mView.showToast()
                mView.removeProgressBar()
            })
        compositeDisposable.add(data)
    }

    override fun setLocationData(lat: Double, long: Double) {
        if (lat != latitude && long != longitude) {
            loadForecast(lat, long)
            longitude = long
            latitude = lat
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    private fun createForecastList(responce: Responce): ArrayList<ForecastItem> {
        val forecastList = ArrayList<ForecastItem>()
        val list = responce.list
        list?.map { it1 ->
            val forecast = ForecastItem()
            val isDay = it1.sys?.pod
            forecast.isDay = isDay == "d"
            forecast.imageId = it1.weatherList?.get(0)?.id
            forecast.temperature = it1.main?.temp?.toInt().toString() + " ℃"
            forecast.weatherDescription =
                it1.weatherList?.get(0)?.description?.makeFirstCharUpper()
            forecast.time = it1.dtTxt?.getTime()
            forecast.dayOfWeek = it1.dt?.getDayOfWeek()
            forecastList.add(forecast)
        }
        return forecastList
    }

    private fun createForecastWithNoData(): ArrayList<ForecastItem> {
        val forecastNoData = ForecastItem()
        val noData = "No data"
        with(forecastNoData) {
            imageId = 200
            temperature = noData
            weatherDescription = noData
            time = noData
        }
        val list = ArrayList<ForecastItem>()
        for (i in 1..10) {
            list.add(forecastNoData)
        }
        return list
    }
}

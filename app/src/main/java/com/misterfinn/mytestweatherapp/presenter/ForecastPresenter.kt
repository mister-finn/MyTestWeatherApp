package com.misterfinn.mytestweatherapp.presenter

import android.util.Log
import com.misterfinn.mytestweatherapp.api.ApiFactory
import com.misterfinn.mytestweatherapp.pojo.ForecastItem
import com.misterfinn.mytestweatherapp.utils.getDayOfWeek
import com.misterfinn.mytestweatherapp.utils.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ForecastPresenter(_mView: MainContract.ForecastView) :
    MainContract.Presenter {
    private val compositeDisposable = CompositeDisposable()
    private val mView: MainContract.ForecastView = _mView

    private fun loadForecast(lat: Double, lon: Double) {
        Log.d("tesst","$lat $lon,loadForecast")
        val data = ApiFactory.apiService.loadData(lat, lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val forecastList = ArrayList<ForecastItem>()
                val list = it.list
                list?.map { it1 ->
                    val forecast = ForecastItem()
                    val isDay = it1.sys?.pod
                    forecast.isDay = isDay == "d"
                    forecast.imageId = it1.weatherList?.get(0)?.id
                    forecast.temperature = it1.main?.temp?.toInt().toString() + " ℃"
                    forecast.weatherDescription = it1.weatherList?.get(0)?.description
                    forecast.time = it1.dtTxt?.getTime()
                    forecast.dayOfWeek = it1.dt?.getDayOfWeek()
                    forecastList.add(forecast)
                }
                mView.showForecast(forecastList)
            }, {
                Log.e("tesst", "${it.message}")
            })
        compositeDisposable.add(data)
    }

    override fun setLocationData(lat: Double, long: Double) {
        loadForecast(lat, long)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}
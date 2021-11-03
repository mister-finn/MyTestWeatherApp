package com.misterfinn.mytestweatherapp.presenter

import com.misterfinn.mytestweatherapp.api.ApiFactory
import com.misterfinn.mytestweatherapp.pojo.ListInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter(_mView: MainContract.View) : MainContract.Presenter {

    private var weatherList: ArrayList<ListInfo>
    private val compositeDisposable = CompositeDisposable()
    private val mView: MainContract.View

    init {
        weatherList = loadData()
        mView = _mView
    }

    override fun showList() {
        mView.displayInfo(weatherList)
    }

    private fun loadData(): ArrayList<ListInfo> {
        var result = ArrayList<ListInfo>()
        val data = ApiFactory.apiService.loadData()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result = it.list as ArrayList<ListInfo>
            }, {})
        compositeDisposable.add(data)
        return result
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}
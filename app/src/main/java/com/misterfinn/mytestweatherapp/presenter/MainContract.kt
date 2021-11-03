package com.misterfinn.mytestweatherapp.presenter

import com.misterfinn.mytestweatherapp.pojo.ListInfo

interface MainContract {
    interface View {
        fun displayInfo(weatherList: List<ListInfo>)
    }

    interface Presenter {
        fun showList()
        fun onDestroy()
    }

}
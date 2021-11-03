package com.misterfinn.mytestweatherapp.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.misterfinn.mytestweatherapp.R
import com.misterfinn.mytestweatherapp.pojo.ListInfo
import com.misterfinn.mytestweatherapp.presenter.MainContract
import com.misterfinn.mytestweatherapp.presenter.MainPresenter

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var presenter:MainPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
    }

    override fun displayInfo(weatherList: List<ListInfo>) {
        
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
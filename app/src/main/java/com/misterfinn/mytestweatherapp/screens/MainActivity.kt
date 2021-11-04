package com.misterfinn.mytestweatherapp.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.misterfinn.mytestweatherapp.fragments.ForecastFragment
import com.misterfinn.mytestweatherapp.R
import com.misterfinn.mytestweatherapp.fragments.TodayWeatherFragment
import com.misterfinn.mytestweatherapp.databinding.ActivityMainBinding
import com.misterfinn.mytestweatherapp.pojo.ListInfo
import com.misterfinn.mytestweatherapp.presenter.MainContract
import com.misterfinn.mytestweatherapp.presenter.MainPresenter

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var presenter: MainPresenter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainPresenter(this)
        val todayFragment = TodayWeatherFragment()
        val forecastFragment = ForecastFragment()
        setCurrentFragment(todayFragment)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.todayMenuItem -> {
                    setCurrentFragment(todayFragment)
                    Log.i("tesst","Today pressed")
                }
                R.id.forecastMenuItem -> {
                    setCurrentFragment(forecastFragment)
                    Log.i("tesst","Forecast pressed")
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun displayInfo(weatherList: List<ListInfo>) {

    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            Log.e("tesst","currentFragment ${fragment.javaClass}")
            replace(R.id.fragmentsContainer, fragment)
            commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
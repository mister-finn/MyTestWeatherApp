package com.misterfinn.mytestweatherapp.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.misterfinn.mytestweatherapp.fragments.ForecastFragment
import com.misterfinn.mytestweatherapp.R
import com.misterfinn.mytestweatherapp.fragments.TodayWeatherFragment
import com.misterfinn.mytestweatherapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val todayFragment = TodayWeatherFragment()
        val forecastFragment = ForecastFragment()
        setCurrentFragment(todayFragment)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.todayMenuItem -> {
                    setCurrentFragment(todayFragment)
                }
                R.id.forecastMenuItem -> {
                    setCurrentFragment(forecastFragment)
                }
            }
            return@setOnItemSelectedListener true
        }
    }


    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentsContainer, fragment)
            commit()
        }
    }
}
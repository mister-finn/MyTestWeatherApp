package com.misterfinn.mytestweatherapp.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.misterfinn.mytestweatherapp.R
import com.misterfinn.mytestweatherapp.databinding.FragmentTodayWeatherBinding


class TodayWeatherFragment : Fragment(R.layout.fragment_today_weather) {

    private var binding: FragmentTodayWeatherBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodayWeatherBinding.bind(view)

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
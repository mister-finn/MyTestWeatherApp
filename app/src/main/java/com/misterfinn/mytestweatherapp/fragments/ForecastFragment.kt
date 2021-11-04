package com.misterfinn.mytestweatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.misterfinn.mytestweatherapp.R
import com.misterfinn.mytestweatherapp.databinding.FragmentForecastBinding


class ForecastFragment : Fragment(R.layout.fragment_forecast) {
    private var binding: FragmentForecastBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForecastBinding.bind(view)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
package com.misterfinn.mytestweatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.misterfinn.mytestweatherapp.R
import com.misterfinn.mytestweatherapp.adapters.MainAdapter
import com.misterfinn.mytestweatherapp.databinding.FragmentForecastBinding
import com.misterfinn.mytestweatherapp.pojo.ForecastItem
import com.misterfinn.mytestweatherapp.presenter.ForecastPresenter
import com.misterfinn.mytestweatherapp.presenter.MainContract


class ForecastFragment : Fragment(R.layout.fragment_forecast), MainContract.ForecastView {
    private var binding: FragmentForecastBinding? = null
    private lateinit var presenter: ForecastPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForecastBinding.bind(view)
        presenter = ForecastPresenter(this)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(activity?.applicationContext)
    }

    override fun showForecast(list: ArrayList<ForecastItem>) {
        val adapter = MainAdapter(list)
        binding?.recyclerView?.adapter = adapter
    }

    override fun onDestroyView() {
        binding = null
        presenter.onDestroy()
        super.onDestroyView()
    }
}
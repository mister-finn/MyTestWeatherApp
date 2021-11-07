package com.misterfinn.mytestweatherapp.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.misterfinn.mytestweatherapp.R
import com.misterfinn.mytestweatherapp.adapters.MainAdapter
import com.misterfinn.mytestweatherapp.databinding.FragmentForecastBinding
import com.misterfinn.mytestweatherapp.pojo.ForecastItem
import com.misterfinn.mytestweatherapp.presenter.ForecastPresenter
import com.misterfinn.mytestweatherapp.presenter.MainContract


class ForecastFragment : Fragment(R.layout.fragment_forecast), MainContract.ForecastView,
    LocationListener {
    private var binding: FragmentForecastBinding? = null
    private lateinit var presenter: ForecastPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForecastBinding.bind(view)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(activity?.applicationContext)
        presenter = ForecastPresenter(this)
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }

        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
    }

    override fun onLocationChanged(location: Location) {
        presenter.setLocationData(location.latitude,location.longitude)
    }

    override fun showToast() {
        Toast.makeText(this.context, "Check your Network connection", Toast.LENGTH_SHORT).show()
    }

    override fun showForecast(list: ArrayList<ForecastItem>) {
        val adapter = MainAdapter()
        adapter.list = list
        binding?.recyclerView?.adapter = adapter
    }

    override fun onDestroyView() {
        binding = null
        presenter.onDestroy()
        super.onDestroyView()
    }
}
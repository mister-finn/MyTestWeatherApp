package com.misterfinn.mytestweatherapp.fragments

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
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
    private lateinit var sharedPreferences: SharedPreferences
    private val appPreferencesLatitude = "Latitude"
    private val appPreferencesLongitude = "Longitude"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForecastBinding.bind(view)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(activity?.applicationContext)
        presenter = ForecastPresenter(this)
        sharedPreferences = requireActivity().getSharedPreferences("location", Context.MODE_PRIVATE)
        getLocationFromCash()
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
        } else {
            ActivityCompat.requestPermissions(
                this.requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        }

        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, this)
        binding?.forecastProgressBar?.visibility = View.VISIBLE
    }

    override fun onLocationChanged(location: Location) {
        val latitude = location.latitude.toFloat()
        val longitude = location.longitude.toFloat()
        sharedPreferences.edit().putFloat(appPreferencesLatitude, latitude).apply()
        sharedPreferences.edit().putFloat(appPreferencesLongitude, longitude).apply()
        presenter.setLocationData(location.latitude, location.longitude)
    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onProviderDisabled(provider: String) {
        if (this.context != null) {
            Toast.makeText(this.context, "Check your location settings!", Toast.LENGTH_SHORT).show()
        }
        getLocationFromCash()
    }

    override fun removeProgressBar() {
        binding?.forecastProgressBar?.visibility = View.GONE
    }

    override fun showToast() {
        Toast.makeText(this.context, "Check your Network connection", Toast.LENGTH_SHORT).show()
    }

    override fun showCity(city: String?) {
        binding?.textViewCity?.text = city
    }

    override fun showForecast(list: ArrayList<ForecastItem>) {
        val adapter = MainAdapter(list)
        binding?.recyclerView?.adapter = adapter
        presenter.onDestroy()
    }

    private fun getLocationFromCash() {
        if (sharedPreferences.contains(appPreferencesLongitude) && sharedPreferences.contains(
                appPreferencesLatitude
            )
        ) {
            val longitude = sharedPreferences.getFloat(appPreferencesLongitude, 0.0f).toDouble()
            val latitude = sharedPreferences.getFloat(appPreferencesLatitude, 0.0f).toDouble()
            presenter.setLocationData(latitude, longitude)
        }
    }

    override fun onDestroyView() {
        binding = null
        presenter.onDestroy()
        super.onDestroyView()
    }
}
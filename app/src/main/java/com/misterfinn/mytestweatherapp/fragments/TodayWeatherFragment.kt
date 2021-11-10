package com.misterfinn.mytestweatherapp.fragments


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.misterfinn.mytestweatherapp.R
import com.misterfinn.mytestweatherapp.databinding.FragmentTodayWeatherBinding
import com.misterfinn.mytestweatherapp.pojo.TodayWeather
import com.misterfinn.mytestweatherapp.presenter.MainContract
import com.misterfinn.mytestweatherapp.presenter.TodayWeatherPresenter


class TodayWeatherFragment : Fragment(R.layout.fragment_today_weather),
    MainContract.TodayWeatherView, LocationListener {

    private var binding: FragmentTodayWeatherBinding? = null
    private lateinit var presenter: TodayWeatherPresenter
    private val appPreferencesLatitude = "Latitude"
    private val appPreferencesLongitude = "Longitude"
    private lateinit var sharedPreferences: SharedPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodayWeatherBinding.bind(view)
        presenter = TodayWeatherPresenter(this)
        sharedPreferences = requireActivity().getSharedPreferences("location", Context.MODE_PRIVATE)
        getLocationFromCash()
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        }

        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, this)
        binding?.textViewShare?.visibility = View.INVISIBLE
        binding?.progressBar?.visibility = View.VISIBLE
    }

    override fun removeProgressBar() {
        binding?.progressBar?.visibility = View.GONE
    }

    override fun showToast() {
        Toast.makeText(this.context, "Check your Network connection", Toast.LENGTH_SHORT).show()
    }

    override fun showTodayWeather(todayWeather: TodayWeather) {
        bindingFragment(todayWeather)
    }

    override fun onLocationChanged(location: Location) {
        val latitude = location.latitude.toFloat()
        val longitude = location.longitude.toFloat()
        sharedPreferences.edit().putFloat(appPreferencesLatitude, latitude).apply()
        sharedPreferences.edit().putFloat(appPreferencesLongitude, longitude).apply()
        presenter.setLocationData(location.latitude, location.longitude)
    }

    override fun onProviderDisabled(provider: String) {
        if (this.context != null) {
            Toast.makeText(this.context, "Check your location settings!", Toast.LENGTH_SHORT).show()
        }
        getLocationFromCash()
    }

    override fun onProviderEnabled(provider: String) {
    }

    private fun bindingFragment(todayWeather: TodayWeather) {
        setImages(todayWeather)
        with(todayWeather) {
            binding?.textViewCity?.text = cityAndCountry
            binding?.textViewTemperature?.text = temperature
            binding?.textViewWeatherDescription?.text = weatherDescription
            binding?.textViewHumidity?.text = humidity
            binding?.textViewRainfall?.text = rainfall
            binding?.textViewPressure?.text = pressure
            binding?.textViewWindSpeed?.text = windSpeed
            binding?.textViewWindDirection?.text = windDirection
        }
        binding?.textViewShare?.visibility = View.VISIBLE
        binding?.textViewShare?.setOnClickListener {
            createIntent(todayWeather)
        }
    }

    private fun setImages(todayWeather: TodayWeather) {
        when (todayWeather.imageId) {
            in 200..232 -> binding?.imageViewWeather?.setImageResource(R.drawable.ic_thunderstorm)
            in 300..321 -> binding?.imageViewWeather?.setImageResource(R.drawable.ic_shower_rain)
            in 500..531 -> binding?.imageViewWeather?.setImageResource(R.drawable.ic_rain)
            in 600..622 -> binding?.imageViewWeather?.setImageResource(R.drawable.ic_snow)
            in 701..781 -> binding?.imageViewWeather?.setImageResource(R.drawable.ic_mist)
            800 -> {
                if (todayWeather.isDay) {
                    binding?.imageViewWeather?.setImageResource(R.drawable.ic_clear_sky_day)
                } else {
                    binding?.imageViewWeather?.setImageResource(R.drawable.ic_clear_sky_night)
                }
            }
            801 -> {
                if (todayWeather.isDay) {
                    binding?.imageViewWeather?.setImageResource(R.drawable.ic_few_clouds_day)
                } else {
                    binding?.imageViewWeather?.setImageResource(R.drawable.ic_few_clouds_night)
                }
            }
            802, 803, 804 -> binding?.imageViewWeather?.setImageResource(R.drawable.ic_clouds)
            else -> binding?.imageViewWeather?.setImageResource(R.drawable.ic_wind_direction)
        }
    }

    private fun createIntent(todayWeather: TodayWeather) {
        val intent = Intent(Intent.ACTION_SEND)
        var result: String
        with(todayWeather) {
            result =
                "Location:$cityAndCountry, temperature:$temperature, $weatherDescription, " +
                        "rainfall:$rainfall, humidity:$humidity, pressure:$pressure, wind direction:" +
                        "$windDirection, wind speed:$windSpeed"
        }
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
        intent.putExtra(Intent.EXTRA_TEXT, result)
        startActivity(Intent.createChooser(intent, getString(R.string.send_info_about_weather)))
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

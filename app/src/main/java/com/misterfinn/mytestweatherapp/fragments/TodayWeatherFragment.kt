package com.misterfinn.mytestweatherapp.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.misterfinn.mytestweatherapp.R
import com.misterfinn.mytestweatherapp.databinding.FragmentTodayWeatherBinding
import com.misterfinn.mytestweatherapp.pojo.TodayWeather
import com.misterfinn.mytestweatherapp.presenter.MainContract
import com.misterfinn.mytestweatherapp.presenter.TodayWeatherPresenter


class TodayWeatherFragment : Fragment(R.layout.fragment_today_weather), MainContract.TodayWeatherView{

    private var binding: FragmentTodayWeatherBinding? = null
    private lateinit var presenter: TodayWeatherPresenter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodayWeatherBinding.bind(view)
        presenter = TodayWeatherPresenter(this)
    }

    override fun showTodayWeather(todayWeather: TodayWeather) {
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


    }

    override fun onDestroyView() {
        binding = null
        presenter.onDestroy()
        super.onDestroyView()
    }
}
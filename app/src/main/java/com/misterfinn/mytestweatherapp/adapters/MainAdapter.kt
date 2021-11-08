package com.misterfinn.mytestweatherapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.misterfinn.mytestweatherapp.R
import com.misterfinn.mytestweatherapp.databinding.ForecastItemBinding
import com.misterfinn.mytestweatherapp.pojo.ForecastItem

class MainAdapter() :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    var list:ArrayList<ForecastItem> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        with(holder) {
            val forecastItem = list[position]
            when (forecastItem.imageId) {
                in 200..232 -> binding.itemImageViewWeather.setImageResource(R.drawable.ic_thunderstorm)
                in 300..321 -> binding.itemImageViewWeather.setImageResource(R.drawable.ic_shower_rain)
                in 500..531 -> binding.itemImageViewWeather.setImageResource(R.drawable.ic_rain)
                in 600..622 -> binding.itemImageViewWeather.setImageResource(R.drawable.ic_snow)
                in 701..781 -> binding.itemImageViewWeather.setImageResource(R.drawable.ic_mist)
                800 -> {
                    if (forecastItem.isDay) {
                        binding.itemImageViewWeather.setImageResource(R.drawable.ic_clear_sky_day)
                    } else {
                        binding.itemImageViewWeather.setImageResource(R.drawable.ic_clear_sky_night)
                    }
                }
                801 -> {
                    if (forecastItem.isDay) {
                        binding.itemImageViewWeather.setImageResource(R.drawable.ic_few_clouds_day)
                    } else {
                        binding.itemImageViewWeather.setImageResource(R.drawable.ic_few_clouds_night)
                    }
                }
                802, 803, 804 -> binding.itemImageViewWeather.setImageResource(R.drawable.ic_clouds)
                else -> binding.itemImageViewWeather.setImageResource(R.drawable.ic_wind_direction)
            }
            binding.itemTextViewTemperature.text = forecastItem.temperature
            binding.itemTextViewTime.text = forecastItem.time
            binding.itemTextViewWeather.text = forecastItem.weatherDescription
            binding.itemTextViewDayOfWeek.visibility = View.GONE
            if (position == 0) {
                binding.itemTextViewDayOfWeek.visibility = View.VISIBLE
                binding.itemTextViewDayOfWeek.setText(R.string.today)

            }
            if (forecastItem.time == "00:00") {
                Log.d("tesst", "timeStamp = ${forecastItem.dayOfWeek}")
                binding.itemTextViewDayOfWeek.visibility = View.VISIBLE
                binding.itemTextViewDayOfWeek.text = forecastItem.dayOfWeek

            }
        }
    }

    override fun getItemCount() = list.size

    inner class MainViewHolder(val binding: ForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
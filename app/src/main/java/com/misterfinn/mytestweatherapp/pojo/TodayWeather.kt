package com.misterfinn.mytestweatherapp.pojo

class TodayWeather(
    var isDay: Boolean = false,
    var imageId: Int? = null,
    var cityAndCountry: String? = null,
    var temperature: String? = null,
    var weatherDescription: String? = null,
    var humidity: String? = null,
    var rainfall: String? = null,
    var pressure: String? = null,
    var windSpeed: String? = null,
    var windDirection: String? = null
)

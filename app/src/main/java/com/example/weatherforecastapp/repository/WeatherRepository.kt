package com.example.weatherforecastapp.repository

import android.util.Log
import com.example.weatherforecastapp.data.DataOrException
import com.example.weatherforecastapp.model.Weather
import com.example.weatherforecastapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
     suspend fun getWeather(cityQuery: String, units: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(city = cityQuery, units = units)

        } catch (e: Exception) {
            Log.d("REZX", "$e")
            return DataOrException(e = e)
        }
         Log.d("Insi", "$response")
         return DataOrException(data = response)
     }
}
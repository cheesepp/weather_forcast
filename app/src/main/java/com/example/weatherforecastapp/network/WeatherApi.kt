package com.example.weatherforecastapp.network

import com.example.weatherforecastapp.model.Weather
import com.example.weatherforecastapp.utils.Constant
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
//    @GET("data/2.5/forecast")
//    suspend fun getWeather(
//        @Query("q") query: String,
//        @Query("units") units: String = "imperial",
//        @Query("appid") appid: String = Constant.API_KEY
//    ): Weather
    @GET("forecast/daily")
    suspend fun getWeather(
    @Query("city") city:String,
    @Query("units") units: String = "M",
    @Query("key") key:String = Constant.API_KEY
    ): Weather
}
package com.example.weatherforecastapp.model


import com.google.gson.annotations.SerializedName

data class WeatherItem(
    @SerializedName("code")
    val code: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)
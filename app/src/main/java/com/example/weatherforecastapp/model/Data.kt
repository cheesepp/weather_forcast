package com.example.weatherforecastapp.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("app_max_temp")
    val appMaxTemp: Double,
    @SerializedName("app_min_temp")
    val appMinTemp: Double,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("clouds_hi")
    val cloudsHi: Int,
    @SerializedName("clouds_low")
    val cloudsLow: Int,
    @SerializedName("clouds_mid")
    val cloudsMid: Int,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("dewpt")
    val dewpt: Double,
    @SerializedName("high_temp")
    val highTemp: Double,
    @SerializedName("low_temp")
    val lowTemp: Double,
    @SerializedName("max_dhi")
    val maxDhi: Any,
    @SerializedName("max_temp")
    val maxTemp: Double,
    @SerializedName("min_temp")
    val minTemp: Double,
    @SerializedName("moon_phase")
    val moonPhase: Double,
    @SerializedName("moon_phase_lunation")
    val moonPhaseLunation: Double,
    @SerializedName("moonrise_ts")
    val moonriseTs: Int,
    @SerializedName("moonset_ts")
    val moonsetTs: Int,
    @SerializedName("ozone")
    val ozone: Double,
    @SerializedName("pop")
    val pop: Int,
    @SerializedName("precip")
    val precip: Double,
    @SerializedName("pres")
    val pres: Double,
    @SerializedName("rh")
    val rh: Int,
    @SerializedName("slp")
    val slp: Double,
    @SerializedName("snow")
    val snow: Int,
    @SerializedName("snow_depth")
    val snowDepth: Int,
    @SerializedName("sunrise_ts")
    val sunriseTs: Int,
    @SerializedName("sunset_ts")
    val sunsetTs: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("ts")
    val ts: Int,
    @SerializedName("uv")
    val uv: Double,
    @SerializedName("valid_date")
    val validDate: String,
    @SerializedName("vis")
    val vis: Double,
    @SerializedName("weather")
    val weather: WeatherItem,
    @SerializedName("wind_cdir")
    val windCdir: String,
    @SerializedName("wind_cdir_full")
    val windCdirFull: String,
    @SerializedName("wind_dir")
    val windDir: Int,
    @SerializedName("wind_gust_spd")
    val windGustSpd: Double,
    @SerializedName("wind_spd")
    val windSpd: Double
)
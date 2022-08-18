package com.example.weatherforecastapp.data

class DataOrException<T, Boolean, E:Exception>(
    val data: T? = null,
    var loading: kotlin.Boolean? = null,
    val e: E? = null
) {
}
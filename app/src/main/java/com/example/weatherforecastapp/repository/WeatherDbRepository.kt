package com.example.weatherforecastapp.repository

import com.example.weatherforecastapp.data.WeatherDao
import com.example.weatherforecastapp.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.weatherforecastapp.model.Unit


class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updataFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun getFavById(city:String): Favorite = weatherDao.getFavById(city)

    fun getUnits(): Flow<List<com.example.weatherforecastapp.model.Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)


}
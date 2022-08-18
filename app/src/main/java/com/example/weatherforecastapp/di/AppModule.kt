package com.example.weatherforecastapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherforecastapp.data.WeatherDao
import com.example.weatherforecastapp.data.WeatherDatabase
import com.example.weatherforecastapp.network.WeatherApi
import com.example.weatherforecastapp.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao = weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun providerAppDatabase(@ApplicationContext context: Context): WeatherDatabase = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        "weather_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WeatherApi::class.java)
    }
}
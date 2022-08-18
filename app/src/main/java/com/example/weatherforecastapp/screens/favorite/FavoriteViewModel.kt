package com.example.weatherforecastapp.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapp.model.Favorite
import com.example.weatherforecastapp.repository.WeatherDbRepository
import com.example.weatherforecastapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel() {
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged().collect { listOfFav ->
                if (listOfFav.isNullOrEmpty()) {
                    Log.d("Tag", ": Emtpy")

                } else {
                    _favList.value = listOfFav
                    Log.d("FAV", "${favList.value}")
                }
            }
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch { repository.insertFavorite(favorite = favorite) }

    fun updateFavorite(favorite: Favorite) = viewModelScope.launch { repository.updataFavorite(favorite = favorite) }

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch { repository.deleteFavorite(favorite = favorite) }


}
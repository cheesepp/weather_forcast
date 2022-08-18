package com.example.weatherforecastapp.screens.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapp.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.weatherforecastapp.model.Unit
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: WeatherDbRepository
): ViewModel() {
    private val _unitList = MutableStateFlow<List<com.example.weatherforecastapp.model.Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                .collect { listOfUnits ->
                    if (listOfUnits.isNullOrEmpty()) {
                        Log.d("TAG", "${listOfUnits} ")
                    }else {
                        _unitList.value = listOfUnits

                    }

                }

        }
    }

    fun insertUnit(unit:Unit) = viewModelScope.launch { repository.insertUnit(unit) }
    fun updateUnit(unit: Unit) = viewModelScope.launch { repository.updateUnit(unit) }
    fun deleteUnit(unit: Unit) = viewModelScope.launch { repository.deleteUnit(unit) }
    fun deleteAllUnits() = viewModelScope.launch { repository.deleteAllUnits() }


}
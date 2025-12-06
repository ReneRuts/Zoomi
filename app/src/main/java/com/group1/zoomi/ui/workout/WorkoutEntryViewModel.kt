package com.group1.zoomi.ui.workout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group1.zoomi.data.LocationRepository
import com.group1.zoomi.data.Workout
import com.group1.zoomi.data.WorkoutsRepository
import com.group1.zoomi.network.WeatherApi
import kotlinx.coroutines.launch

class WorkoutEntryViewModel(
    private val workoutsRepository: WorkoutsRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    var workoutUiState by mutableStateOf(WorkoutUiState())

    // get the weather as soon as the viewmodel is created
    init {
        fetchWeatherForWorkout()
    }
    fun updateUiState(newWorkoutUiState: WorkoutUiState) {
        workoutUiState = newWorkoutUiState.copy()

    }

    private fun fetchWeatherForWorkout() {
        viewModelScope.launch {
            val location = locationRepository.getCurrentLocation()
            location?.let {
                // this is an unsecure call to the API because the user can intercept and change the data
                val weatherData = WeatherApi.retrofitService.getWeather(it.latitude, it.longitude)
                val weatherString = "${weatherData.currentWeather.temperature}°C\n${weatherData.currentWeather.windspeed} km/h"
                updateUiState(workoutUiState.copy(weatherInfo = weatherString))
            }
        }
    }

    suspend fun saveWorkout() {
        if (workoutUiState.isValid()) {
            workoutsRepository.addWorkout(workoutUiState.toWorkout())
        }
    }

    data class WorkoutUiState(
        val workoutId: Int = 0,
        val type: String = "",
        val title: String = "",
        val durationHours: String = "",
        val durationMinutes: String = "",
        val weatherInfo: String = "",
        val imagePath: String? = null
    ) {
        fun isValid(): Boolean {
            return type.isNotBlank()
                    && title.isNotBlank()
                    && durationHours.isNotBlank()
                    && durationMinutes.isNotBlank()
                    && weatherInfo.isNotBlank()
        }

        fun toWorkout(): Workout {
            val hours = durationHours.toIntOrNull() ?: 0
            val minutes = durationMinutes.toIntOrNull() ?: 0

            return Workout(
                workoutId = workoutId,
                type = type,
                title = title,
                durationHours = hours.coerceIn(0,23),
                durationMinutes = minutes.coerceIn(0,59),
                weatherInfo = weatherInfo,
                imagePath = imagePath
            )
        }
    }
}
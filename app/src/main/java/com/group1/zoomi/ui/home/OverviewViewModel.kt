package com.group1.zoomi.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group1.zoomi.data.LocationRepository
import com.group1.zoomi.data.Workout
import com.group1.zoomi.data.WorkoutsRepository
import com.group1.zoomi.model.LocationData
import com.group1.zoomi.model.WeatherData
import com.group1.zoomi.network.WeatherApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class OverviewViewModel(
    private val workoutsRepository: WorkoutsRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    val overviewUiState: StateFlow<OverviewUiState> = workoutsRepository.getAllWorkouts().map { OverviewUiState(it) }
        .stateIn(
            scope = viewModelScope,
//            deze lijn zorgt ervoor dat het enkel items refresht als er naar de ui pagina gekeken wordt
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = OverviewUiState()
        )

    private val _locationState = MutableStateFlow<LocationData?>(null)
    val locationState: StateFlow<LocationData?> = _locationState

    private val _weatherState = MutableStateFlow<WeatherData?>(null)
    val weatherState: StateFlow<WeatherData?> = _weatherState

    private val _rainChanceState = MutableStateFlow<Int?>(null)
    val rainChanceState: StateFlow<Int?> = _rainChanceState

    private val _locationPermissionDenied = MutableStateFlow(false)
    val locationPermissionDenied: StateFlow<Boolean> = _locationPermissionDenied

    fun setLocationPermissionDenied(value: Boolean) {
        _locationPermissionDenied.value = value
    }

    fun fetchLocation() {
        viewModelScope.launch {
            val location = locationRepository.getCurrentLocation()
            _locationState.value = location
            location?.let {
                val weatherData = WeatherApi.retrofitService.getWeather(it.latitude, it.longitude)
                _weatherState.value = weatherData
                _rainChanceState.value = getRainChanceForToday(weatherData)
            }
        }
    }

    private fun getRainChanceForToday(weather: WeatherData?): Int? {

        if(weather?.daily == null) return null
        val currentDate = weather.currentWeather.time.take(10) // take is hetzelfde als substring ma me 1 parameter
        val todayIndex = weather.daily.time.indexOf(currentDate)

        return if (todayIndex != -1){
            weather.daily.precipitationProbabilityMax[todayIndex]
        } else {
            null
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    
}

//dit is een lege lijst waar onze data kan worden opgeslagen om het dan tonen in onze overviewscreen
data class OverviewUiState(val workoutList: List<Workout> = listOf())

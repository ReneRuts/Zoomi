package com.group1.zoomi.ui.home

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

    fun fetchLocation() {
        viewModelScope.launch {
            val location = locationRepository.getCurrentLocation()
            _locationState.value = location
            location?.let {
                _weatherState.value = WeatherApi.retrofitService.getWeather(it.latitude, it.longitude)
            }
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    
}

//dit is een lege lijst waar onze data kan worden opgeslagen om het dan tonen in onze overviewscreen
data class OverviewUiState(val workoutList: List<Workout> = listOf())

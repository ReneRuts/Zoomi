package com.group1.zoomi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group1.zoomi.data.Workout
import com.group1.zoomi.data.WorkoutsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class OverviewViewModel(workoutsRepository: WorkoutsRepository) : ViewModel() {

    val overviewUiState: StateFlow<OverviewUiState> = workoutsRepository.getAllWorkouts().map { OverviewUiState(it) }
        .stateIn(
            scope = viewModelScope,
//            deze lijn zorgt ervoor dat het enkel items refresht als er naar de ui pagina gekeken wordt
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = OverviewUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    
}

//dit is een lege lijst waar onze data kan worden opgeslagen om het dan tonen in onze overviewscreen
data class OverviewUiState(val workoutList: List<Workout> = listOf())

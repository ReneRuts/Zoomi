package com.group1.zoomi.ui.detail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group1.zoomi.data.Workout
import com.group1.zoomi.data.WorkoutsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailsViewModel(
    private val workoutsRepository: WorkoutsRepository
) : ViewModel() {
    fun getWorkoutDetails(workoutId: Int): StateFlow<Workout?> =
        workoutsRepository.getWorkout(workoutId)
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = null
            )

    fun formatWorkoutDetails(workout: Workout): String {
        return """
            Title: ${workout.title}
            Type: ${workout.type}
            Duration: ${workout.durationHours} hours and ${workout.durationMinutes} minutes
            Weather: ${workout.weatherInfo}
        """.trimIndent()
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
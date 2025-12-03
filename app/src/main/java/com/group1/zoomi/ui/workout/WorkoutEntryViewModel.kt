package com.group1.zoomi.ui.workout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.group1.zoomi.data.Workout
import com.group1.zoomi.data.WorkoutsRepository

class WorkoutEntryViewModel(private val workoutsRepository: WorkoutsRepository) : ViewModel() {
    var workoutUiState by mutableStateOf(WorkoutUiState())

    fun updateUiState(newWorkoutUiState: WorkoutUiState) {
        workoutUiState = newWorkoutUiState.copy()

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
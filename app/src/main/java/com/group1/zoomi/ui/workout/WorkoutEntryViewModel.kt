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
        val duration: String = "",
        val weatherInfo: String = "",
        val imagePath: String? = null
    ) {
        fun isValid(): Boolean {
            return type.isNotBlank() && title.isNotBlank() && duration.isNotBlank() && weatherInfo.isNotBlank()
        }

        fun toWorkout(): Workout = Workout(
            workoutId = workoutId,
            type = type,
            title = title,
            duration = duration.toIntOrNull() ?: 0,
            weatherInfo = weatherInfo,
            imagePath = imagePath
        )
    }
}
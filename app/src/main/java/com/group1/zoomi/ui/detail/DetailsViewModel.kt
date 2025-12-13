package com.group1.zoomi.ui.detail


import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group1.zoomi.R
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

    fun saveWorkoutDetails(context: Context, workout: Workout) {
        val formattedWorkout = formatWorkoutDetails(workout)
        val filename = "${workout.title.replace(" ", "_")}.txt"

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/Zoomi")
            }
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            try {
                resolver.openOutputStream(uri)?.use { outputStream ->
                    outputStream.write(formattedWorkout.toByteArray())
                    Toast.makeText(context, context.getString(R.string.workout_saved_successfully), Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, context.getString(R.string.error_saving_file), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, context.getString(R.string.error_creating_file), Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
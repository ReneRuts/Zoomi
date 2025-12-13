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
        val formattedWorkout = formatWorkoutDetails(workout) //saves the formatted workout
        val filename = "${workout.title.replace(" ", "_")}.txt" //creates the title for the file

//        deze block zet de metadata klaar, de eerste put zegt de gsm de filename die we gemaakt hebben,
//        de 2de put zegt tegen de gsm welke soort file het is,
//        de if functie checkt welke android versie de gsm heeft en als het een nieuwere verse is(android 10 of nieuwer)
//        dan maakt hij een aparte subfolder zoomi aan in de downloads folder om dan daar de workout op te slaan.
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/Zoomi")
            }
        }

        val resolver = context.contentResolver //dit zorgt ervoor dat onze app kan communiceren met mediastore api

        //hier wordt de file echt aangemaakt en opgeslagen
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        //dit checkt of uri is null. als dat zo is de file aangemaakt en kunnen we er data naar schrijven
        //het try block schrijft dan de data naar de file, de use block zorgt ervoor dat als het klaar is alles veilig afgesloten wordt zelfs als er een error zou gebeuren
        //dan alle Toast delen maken een pop up melding dat de workout gesaved is of dat het fout gegaan is etc.
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
package com.group1.zoomi

import android.app.Application
import com.group1.zoomi.data.AppContainer
import com.group1.zoomi.data.AppDataContainer
import com.group1.zoomi.data.Workout
import com.group1.zoomi.data.ZoomiDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ZoomiApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

        // bij het opstarten van de app maakt hij deze testdata uit, comment het uit om niet te hebben
        CoroutineScope(Dispatchers.IO).launch {
            val workoutDao = ZoomiDatabase.getDatabase(this@ZoomiApplication).workoutDao()
            workoutDao.clearAll()
            workoutDao.insert(
                Workout(
                    type = "Running",
                    title = "Morning Park Run",
                    durationHours = 12,
                    durationMinutes = 30,
                    weatherInfo = "15°C, Sunny",
                    imagePath = "test"
                )
            )
            workoutDao.insert(
                Workout(
                    type = "Weightlifting",
                    title = "Full Body Strength",
                    durationHours = 0,
                    durationMinutes = 15,
                    weatherInfo = "Indoors",
                    imagePath = null
                )
            )
            workoutDao.insert(
                Workout(
                    type = "Yoga",
                    title = "Evening Relaxation",
                    durationHours = 0,
                    durationMinutes = 45,
                    weatherInfo = "Indoors",
                    imagePath = null
                )
            )
        }
        // einde van het stuk voor testdata uit te commenten
    }
}

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

        CoroutineScope(Dispatchers.IO).launch {
            val workoutDao = ZoomiDatabase.getDatabase(this@ZoomiApplication).workoutDao()
            workoutDao.clearAll()
            workoutDao.resetPrimaryKey()
            workoutDao.insert(
                Workout(
                    type = "Running",
                    title = "Morning Park Run",
                    durationHours = 1,
                    durationMinutes = 30,
                    weatherInfo = "15°C, Sunny",
                    minHeartbeat = 134,
                    maxHeartbeat = 210,
                    distance = 10.5
                )
            )
            workoutDao.insert(
                Workout(
                    type = "Weight Training",
                    title = "Full Body Strength",
                    durationHours = 0,
                    durationMinutes = 15,
                    weatherInfo = "Indoors",
                    minHeartbeat = 100,
                    maxHeartbeat = 140,
                    distance = null
                )
            )
            workoutDao.insert(
                Workout(
                    type = "Climbing",
                    title = "Just the Mount Everest",
                    durationHours = 0,
                    durationMinutes = 45,
                    weatherInfo = "Freezing cold!!!!!!!!, no air",
                    minHeartbeat = null,
                    maxHeartbeat = null,
                    distance = null
                )
            )
        }
        // einde van het stuk voor testdata uit te commenten
    }
}

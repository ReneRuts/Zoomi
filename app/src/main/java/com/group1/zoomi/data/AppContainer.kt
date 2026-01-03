package com.group1.zoomi.data

import android.content.Context
import com.google.android.gms.location.LocationServices

interface AppContainer {
    val workoutsRepository: WorkoutsRepository
    val locationRepository: LocationRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val workoutsRepository: WorkoutsRepository by lazy {
        WorkoutsRepository(ZoomiDatabase.getDatabase(context).workoutDao())
    }

    override val locationRepository: LocationRepository by lazy {
        val fusedClient = LocationServices.getFusedLocationProviderClient(context)
        LocationRepository(context, fusedClient)
    }
}

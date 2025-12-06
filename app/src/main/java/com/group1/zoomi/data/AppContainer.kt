package com.group1.zoomi.data

import android.content.Context
import com.google.android.gms.location.LocationServices

/**
 * An app container that is responsible for providing dependencies.
 */
interface AppContainer {
    val workoutsRepository: WorkoutsRepository
    val locationRepository: LocationRepository
}

/**
 * [AppContainer] implementation that provides instance of [WorkoutsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [workoutsRepository]
     */
    override val workoutsRepository: WorkoutsRepository by lazy {
        WorkoutsRepository(ZoomiDatabase.getDatabase(context).workoutDao())
    }

    override val locationRepository: LocationRepository by lazy {
        val fusedClient = LocationServices.getFusedLocationProviderClient(context)
        LocationRepository(context, fusedClient)
    }
}

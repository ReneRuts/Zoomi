package com.group1.zoomi.data

import android.content.Context

/**
 * An app container that is responsible for providing dependencies.
 */
interface AppContainer {
    val workoutsRepository: WorkoutsRepository
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
}

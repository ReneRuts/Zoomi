package com.group1.zoomi.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.group1.zoomi.ZoomiApplication
import com.group1.zoomi.ui.detail.DetailsViewModel
import com.group1.zoomi.ui.home.OverviewViewModel
import com.group1.zoomi.ui.workout.WorkoutEntryViewModel

object ZoomiViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            OverviewViewModel(
                zoomiApplication().container.workoutsRepository,
                zoomiApplication().container.locationRepository
            )
        }

        initializer {
            WorkoutEntryViewModel(
                workoutsRepository = zoomiApplication().container.workoutsRepository,
                locationRepository = zoomiApplication().container.locationRepository
                )
        }

        initializer {
            DetailsViewModel(
                workoutsRepository = zoomiApplication().container.workoutsRepository
            )
        }
    }
}

fun CreationExtras.zoomiApplication(): ZoomiApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ZoomiApplication)
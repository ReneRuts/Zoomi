package com.group1.zoomi.data

import com.group1.zoomi.R
import com.group1.zoomi.model.Workouts

class Datasource() {
    fun loadWorkouts(): List<Workouts> {
        return listOf<Workouts>(
            Workouts(R.string.Workout1, imageResourceId = R.drawable.weight_training),
            Workouts(R.string.Workout2, imageResourceId = R.drawable.weight_training),
            Workouts(R.string.Workout3, imageResourceId = R.drawable.weight_training),
            Workouts(R.string.Workout4, imageResourceId = R.drawable.weight_training))
    }
}
package com.group1.zoomi.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val workoutId: Int = 0,
    val type: String,
    val title: String,
    val duration: Int,
    val weatherInfo: String,
    val imagePath: String? = null
)
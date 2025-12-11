package com.group1.zoomi.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val workoutId: Int = 0,
    val type: String,
    val title: String,
    val durationHours: Int,
    val durationMinutes: Int,
    val weatherInfo: String,
    val minHeartbeat: Int?,
    val maxHeartbeat: Int?,
    val distance: Double?
)

package com.group1.zoomi.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workout: Workout)

    @Update
    suspend fun update(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)

    @Query("SELECT * FROM workouts WHERE workoutId = :workoutId")
    fun getWorkout(workoutId : Int): Flow<Workout>

    @Query("SELECT * FROM workouts ORDER BY workoutId DESC")
    fun getAllWorkouts(): Flow<List<Workout>>
}
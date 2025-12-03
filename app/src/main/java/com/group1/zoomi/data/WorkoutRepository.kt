package com.group1.zoomi.data

import kotlinx.coroutines.flow.Flow

class WorkoutRepository(private val workoutDao: WorkoutDao) {

    suspend fun addWorkout(workout: Workout) = workoutDao.insert(workout)
    suspend fun updateWorkout(workout: Workout) = workoutDao.update(workout)
    suspend fun deleteWorkout(workout: Workout) = workoutDao.delete(workout)

    fun getWorkout(workoutId: Int): Flow<Workout> = workoutDao.getWorkout(workoutId)
    fun getAllWorkouts(): Flow<List<Workout>> = workoutDao.getAllWorkouts()
}
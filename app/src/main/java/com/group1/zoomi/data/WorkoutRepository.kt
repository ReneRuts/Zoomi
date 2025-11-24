package com.group1.zoomi.data

import kotlinx.coroutines.flow.Flow

class WorkoutRepository(private val workoutDao: WorkoutDao) {

    suspend fun addWorkout(workout: Workout) = workoutDao.insert(workout)
    suspend fun updateWorkout(workout: Workout) = workoutDao.update(workout)
    suspend fun deleteWorkout(workout: Workout) = workoutDao.delete(workout)

    fun getWorkoutsForUser(userId: Int): Flow<List<Workout>> = workoutDao.getWorkoutsForUser(userId)
    fun getAllWorkouts(): Flow<List<Workout>> = workoutDao.getAllWorkouts()
}
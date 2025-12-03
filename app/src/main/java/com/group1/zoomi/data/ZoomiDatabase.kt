package com.group1.zoomi.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Workout::class],
    version = 1,
    exportSchema = false
)
abstract class ZoomiDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    companion object{
        @Volatile
        private var Instance: ZoomiDatabase? = null

        fun getDatabase(context: Context): ZoomiDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context.applicationContext, ZoomiDatabase::class.java,"zoomi_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
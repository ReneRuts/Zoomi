package com.group1.zoomi.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Workout::class],
    version = 4,
    //number 2 because of the change from duration to durationHours & durationMinutes
    //number 3 from removing the imagePath field
    //number 4 from adding the minHeartbeat, maxHeartbeat and distance fields
    exportSchema = false
)
abstract class ZoomiDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao

    companion object {
        @Volatile
        private var Instance: ZoomiDatabase? = null

        fun getDatabase(context: Context): ZoomiDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext, ZoomiDatabase::class.java, "zoomi_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

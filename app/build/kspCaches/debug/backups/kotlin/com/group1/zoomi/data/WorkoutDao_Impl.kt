package com.group1.zoomi.`data`

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class WorkoutDao_Impl(
  __db: RoomDatabase,
) : WorkoutDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfWorkout: EntityInsertAdapter<Workout>

  private val __deleteAdapterOfWorkout: EntityDeleteOrUpdateAdapter<Workout>

  private val __updateAdapterOfWorkout: EntityDeleteOrUpdateAdapter<Workout>
  init {
    this.__db = __db
    this.__insertAdapterOfWorkout = object : EntityInsertAdapter<Workout>() {
      protected override fun createQuery(): String = "INSERT OR IGNORE INTO `workouts` (`workoutId`,`type`,`title`,`duration`,`weatherInfo`,`imagePath`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Workout) {
        statement.bindLong(1, entity.workoutId.toLong())
        statement.bindText(2, entity.type)
        statement.bindText(3, entity.title)
        statement.bindLong(4, entity.duration.toLong())
        statement.bindText(5, entity.weatherInfo)
        val _tmpImagePath: String? = entity.imagePath
        if (_tmpImagePath == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpImagePath)
        }
      }
    }
    this.__deleteAdapterOfWorkout = object : EntityDeleteOrUpdateAdapter<Workout>() {
      protected override fun createQuery(): String = "DELETE FROM `workouts` WHERE `workoutId` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Workout) {
        statement.bindLong(1, entity.workoutId.toLong())
      }
    }
    this.__updateAdapterOfWorkout = object : EntityDeleteOrUpdateAdapter<Workout>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `workouts` SET `workoutId` = ?,`type` = ?,`title` = ?,`duration` = ?,`weatherInfo` = ?,`imagePath` = ? WHERE `workoutId` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Workout) {
        statement.bindLong(1, entity.workoutId.toLong())
        statement.bindText(2, entity.type)
        statement.bindText(3, entity.title)
        statement.bindLong(4, entity.duration.toLong())
        statement.bindText(5, entity.weatherInfo)
        val _tmpImagePath: String? = entity.imagePath
        if (_tmpImagePath == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpImagePath)
        }
        statement.bindLong(7, entity.workoutId.toLong())
      }
    }
  }

  public override suspend fun insert(workout: Workout): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfWorkout.insert(_connection, workout)
  }

  public override suspend fun delete(workout: Workout): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfWorkout.handle(_connection, workout)
  }

  public override suspend fun update(workout: Workout): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfWorkout.handle(_connection, workout)
  }

  public override fun getWorkout(workoutId: Int): Flow<Workout> {
    val _sql: String = "SELECT * FROM workouts WHERE workoutId = ?"
    return createFlow(__db, false, arrayOf("workouts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, workoutId.toLong())
        val _columnIndexOfWorkoutId: Int = getColumnIndexOrThrow(_stmt, "workoutId")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfWeatherInfo: Int = getColumnIndexOrThrow(_stmt, "weatherInfo")
        val _columnIndexOfImagePath: Int = getColumnIndexOrThrow(_stmt, "imagePath")
        val _result: Workout
        if (_stmt.step()) {
          val _tmpWorkoutId: Int
          _tmpWorkoutId = _stmt.getLong(_columnIndexOfWorkoutId).toInt()
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDuration: Int
          _tmpDuration = _stmt.getLong(_columnIndexOfDuration).toInt()
          val _tmpWeatherInfo: String
          _tmpWeatherInfo = _stmt.getText(_columnIndexOfWeatherInfo)
          val _tmpImagePath: String?
          if (_stmt.isNull(_columnIndexOfImagePath)) {
            _tmpImagePath = null
          } else {
            _tmpImagePath = _stmt.getText(_columnIndexOfImagePath)
          }
          _result = Workout(_tmpWorkoutId,_tmpType,_tmpTitle,_tmpDuration,_tmpWeatherInfo,_tmpImagePath)
        } else {
          error("The query result was empty, but expected a single row to return a NON-NULL object of type 'com.group1.zoomi.`data`.Workout'.")
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllWorkouts(): Flow<List<Workout>> {
    val _sql: String = "SELECT * FROM workouts ORDER BY workoutId DESC"
    return createFlow(__db, false, arrayOf("workouts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfWorkoutId: Int = getColumnIndexOrThrow(_stmt, "workoutId")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDuration: Int = getColumnIndexOrThrow(_stmt, "duration")
        val _columnIndexOfWeatherInfo: Int = getColumnIndexOrThrow(_stmt, "weatherInfo")
        val _columnIndexOfImagePath: Int = getColumnIndexOrThrow(_stmt, "imagePath")
        val _result: MutableList<Workout> = mutableListOf()
        while (_stmt.step()) {
          val _item: Workout
          val _tmpWorkoutId: Int
          _tmpWorkoutId = _stmt.getLong(_columnIndexOfWorkoutId).toInt()
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDuration: Int
          _tmpDuration = _stmt.getLong(_columnIndexOfDuration).toInt()
          val _tmpWeatherInfo: String
          _tmpWeatherInfo = _stmt.getText(_columnIndexOfWeatherInfo)
          val _tmpImagePath: String?
          if (_stmt.isNull(_columnIndexOfImagePath)) {
            _tmpImagePath = null
          } else {
            _tmpImagePath = _stmt.getText(_columnIndexOfImagePath)
          }
          _item = Workout(_tmpWorkoutId,_tmpType,_tmpTitle,_tmpDuration,_tmpWeatherInfo,_tmpImagePath)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun clearAll() {
    val _sql: String = "DELETE FROM workouts"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}

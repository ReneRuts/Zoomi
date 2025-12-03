package com.group1.zoomi.`data`

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ZoomiDatabase_Impl : ZoomiDatabase() {
  private val _workoutDao: Lazy<WorkoutDao> = lazy {
    WorkoutDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "b9a7456649611124b22454cfea43e723", "3a342987a5a3997833066573760ffb10") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `workouts` (`workoutId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `type` TEXT NOT NULL, `title` TEXT NOT NULL, `duration` INTEGER NOT NULL, `weatherInfo` TEXT NOT NULL, `imagePath` TEXT)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b9a7456649611124b22454cfea43e723')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `workouts`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsWorkouts: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsWorkouts.put("workoutId", TableInfo.Column("workoutId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("type", TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("title", TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("duration", TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("weatherInfo", TableInfo.Column("weatherInfo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("imagePath", TableInfo.Column("imagePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysWorkouts: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesWorkouts: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoWorkouts: TableInfo = TableInfo("workouts", _columnsWorkouts, _foreignKeysWorkouts, _indicesWorkouts)
        val _existingWorkouts: TableInfo = read(connection, "workouts")
        if (!_infoWorkouts.equals(_existingWorkouts)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |workouts(com.group1.zoomi.data.Workout).
              | Expected:
              |""".trimMargin() + _infoWorkouts + """
              |
              | Found:
              |""".trimMargin() + _existingWorkouts)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "workouts")
  }

  public override fun clearAllTables() {
    super.performClear(false, "workouts")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(WorkoutDao::class, WorkoutDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun workoutDao(): WorkoutDao = _workoutDao.value
}

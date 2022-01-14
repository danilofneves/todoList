package com.neves.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.neves.data.local.converters.TimeStampConverter
import com.neves.data.model.TaskRoom

@Database(entities = [TaskRoom::class], version = 2)
@TypeConverters(TimeStampConverter::class)
abstract class AppDatabase : RoomDatabase() {

  abstract fun taskDao(): TaskDAO

  companion object {
    private const val DATABASE = "task_db"

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
      val tempInstance =
        INSTANCE
      if (tempInstance != null) {
        return tempInstance
      }
      synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          DATABASE
        ).fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        return instance
      }
    }
  }
}

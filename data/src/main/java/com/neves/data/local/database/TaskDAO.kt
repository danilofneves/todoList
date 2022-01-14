package com.neves.data.local.database

import androidx.room.*
import com.neves.data.model.TaskRoom
import java.util.*

@Dao
interface TaskDAO {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(taskRoom: TaskRoom)

  @Update
  fun update(taskRoom: TaskRoom)

  @Query("SELECT * FROM TaskRoom")
  suspend fun getTaskList(): List<TaskRoom>

  @Query("SELECT * FROM TaskRoom WHERE data BETWEEN :from AND :to")
  suspend fun getTaskWithDateList(from: Date, to:Date): List<TaskRoom>
}

package com.neves.data.local

import com.neves.data.model.TaskRoom
import com.neves.domain.model.Task
import java.util.*


interface LocalDataSource {
    suspend fun fetchWithDate(from: Date, to:Date): List<Task>
    suspend fun fetch(): List<Task>
    suspend fun save(taskRoom: TaskRoom)
    suspend fun update(taskRoom: TaskRoom)
}

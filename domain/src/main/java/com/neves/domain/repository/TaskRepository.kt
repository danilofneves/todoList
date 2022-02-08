package com.neves.domain.repository

import com.neves.domain.model.Task
import java.util.*


interface TaskRepository {
    suspend fun fetchWithDate(from: Date, to:Date): List<Task>
    suspend fun fetch(): List<Task>
    suspend fun save(task: Task)
    suspend fun update(task: Task)
}
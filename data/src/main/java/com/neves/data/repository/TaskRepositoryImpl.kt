package com.neves.data.repository

import com.neves.data.local.LocalDataSource
import com.neves.data.mapper.toTaskRoom
import com.neves.domain.model.Task
import com.neves.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class TaskRepositoryImpl constructor (private val local: LocalDataSource):
    TaskRepository {

    override suspend fun fetch(): List<Task> {
        return withContext(Dispatchers.IO) {
            local.fetch()
        }
    }

    override suspend fun fetchWithDate(from: Date, to:Date): List<Task> {
        return withContext(Dispatchers.IO) {
            local.fetchWithDate(from, to)
        }
    }

    override suspend fun save(task: Task) {
        return withContext(Dispatchers.IO) {
            local.save(task.toTaskRoom())
        }
    }

    override suspend fun update(task: Task) {
        return withContext(Dispatchers.IO) {
            local.update(task.toTaskRoom())
        }
    }
}
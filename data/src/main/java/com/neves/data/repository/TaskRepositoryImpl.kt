package com.neves.data.repository

import com.neves.data.local.LocalDataSource
import com.neves.data.mapper.toTaskRoom
import com.neves.domain.Either
import com.neves.domain.model.Task
import com.neves.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class TaskRepositoryImpl constructor (private val local: LocalDataSource):
    TaskRepository {

    override suspend fun fetch(): Either<List<Task>, Exception> {
        return withContext(Dispatchers.IO) {
            local.fetch()
        }
    }

    override suspend fun fetchWithDate(from: Date, to:Date): Either<List<Task>, Exception> {
        return withContext(Dispatchers.IO) {
            local.fetchWithDate(from, to)
        }
    }

    override suspend fun save(task: Task) : Either<Unit, Exception> {
        return withContext(Dispatchers.IO) {
            local.save(task.toTaskRoom())
        }
    }

    override suspend fun update(task: Task) : Either<Unit, Exception> {
        return withContext(Dispatchers.IO) {
            local.update(task.toTaskRoom())
        }
    }
}
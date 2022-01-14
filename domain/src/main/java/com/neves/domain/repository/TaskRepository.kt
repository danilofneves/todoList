package com.neves.domain.repository

import com.neves.domain.Either
import com.neves.domain.model.Task
import java.util.*


interface TaskRepository {
    suspend fun fetchWithDate(from: Date, to:Date): Either<List<Task>, Exception>
    suspend fun fetch(): Either<List<Task>, Exception>
    suspend fun save(task: Task): Either<Unit, Exception>
    suspend fun update(task: Task): Either<Unit, Exception>
}
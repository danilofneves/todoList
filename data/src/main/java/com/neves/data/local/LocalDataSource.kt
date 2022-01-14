package com.neves.data.local

import com.neves.data.model.TaskRoom
import com.neves.domain.Either
import com.neves.domain.model.Task
import java.util.*


interface LocalDataSource {
    suspend fun fetchWithDate(from: Date, to:Date): Either<List<Task>, Exception>
    suspend fun fetch(): Either<List<Task>, Exception>
    suspend fun save(taskRoom: TaskRoom): Either<Unit, Exception>
    suspend fun update(taskRoom: TaskRoom): Either<Unit, Exception>
}

package com.neves.domain.usecases

import com.neves.domain.Either
import com.neves.domain.model.Task
import com.neves.domain.repository.TaskRepository
import javax.inject.Inject

class FetchTask  @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(): Either<List<Task>, Exception> = repository.fetch()
}
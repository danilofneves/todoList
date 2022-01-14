package com.neves.domain.usecases

import com.neves.domain.Either
import com.neves.domain.exception.TaskException
import com.neves.domain.model.Task
import com.neves.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTask @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task): Either<Unit, Exception> {
        return repository.update(task)
    }
}
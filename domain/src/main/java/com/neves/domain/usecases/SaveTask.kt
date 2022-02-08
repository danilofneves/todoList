package com.neves.domain.usecases

import com.neves.domain.exception.TaskException
import com.neves.domain.model.Task
import com.neves.domain.repository.TaskRepository
import javax.inject.Inject

class SaveTask @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {
        return when {
            task.titulo.isEmpty() -> throw TaskException.EmptyTituloException
            else -> repository.save(task)
        }
    }
}
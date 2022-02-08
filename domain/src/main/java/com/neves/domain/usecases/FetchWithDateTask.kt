package com.neves.domain.usecases

import com.neves.domain.model.Task
import com.neves.domain.repository.TaskRepository
import java.util.*
import javax.inject.Inject

class FetchWithDateTask  @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(from: Date, to:Date): List<Task> = repository.fetchWithDate(from, to)
}
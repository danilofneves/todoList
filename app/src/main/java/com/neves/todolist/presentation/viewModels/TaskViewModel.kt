package com.neves.todolist.presentation.viewModels

import androidx.lifecycle.*
import com.neves.domain.model.Task
import com.neves.domain.usecases.UpdateTask
import com.neves.domain.usecases.FetchWithDateTask
import com.neves.domain.usecases.SaveTask
import com.neves.todolist.presentation.extensions.launchOnUiState
import com.neves.todolist.presentation.mapper.toTaskItemUiState
import com.neves.todolist.presentation.state.TaskItemUiState
import com.neves.todolist.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val fetchWithDateTask: FetchWithDateTask,
    private val updateTask: UpdateTask,
    private val saveTask: SaveTask
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<TaskItemUiState>>> = MutableStateFlow(UiState.Empty)
    val uiState: StateFlow<UiState<List<TaskItemUiState>>> get() = _uiState

    private var currentList: List<TaskItemUiState> = mutableListOf()

    fun getTasks(from: Date, to:Date){
        launchOnUiState(_uiState) {
            val result = fetchWithDateTask(from, to)
            currentList = result.map {
                it.toTaskItemUiState( onTaskCheck = { onCheckTask(it)})
            }

            return@launchOnUiState currentList
        }
    }

    fun insertTask(task: Task) {
        this.launchOnUiState(_uiState) {
            saveTask(task)
            currentList = currentList.plus(task.toTaskItemUiState { onCheckTask(task) })
            return@launchOnUiState currentList
        }
    }

    private fun onCheckTask(task:Task){
        task.changeDone()
        this.launchOnUiState(_uiState) {
            updateTask(task)
            currentList = currentList.map { currentTask ->
                if (currentTask.id == task.id) currentTask.copy(done = task.done)
                else currentTask
            }
            return@launchOnUiState currentList
        }
    }

}
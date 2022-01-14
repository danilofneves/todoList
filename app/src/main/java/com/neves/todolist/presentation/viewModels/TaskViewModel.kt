package com.neves.todolist.presentation.viewModels

import androidx.lifecycle.*
import com.neves.domain.Either
import com.neves.domain.exception.TaskException
import com.neves.domain.model.Task
import com.neves.domain.usecases.UpdateTask
import com.neves.domain.usecases.FetchTask
import com.neves.domain.usecases.FetchWithDateTask
import com.neves.domain.usecases.SaveTask
import com.neves.todolist.presentation.mapper.toTaskItemUiState
import com.neves.todolist.presentation.state.TaskItemUiState
import com.neves.todolist.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val fetchWithDateTask: FetchWithDateTask,
    private val updateTask: UpdateTask,
    private val saveTask: SaveTask
):ViewModel(){

    private val _taskLiveData: MutableStateFlow<List<TaskItemUiState>> = MutableStateFlow(emptyList())
    val taskLiveData: StateFlow<List<TaskItemUiState>> get() = _taskLiveData

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val uiState: StateFlow<UiState> get() = _uiState

    fun getTasks(from: Date, to:Date){
        _uiState.update { UiState.Loading }
        viewModelScope.launch {
            when(val result = fetchWithDateTask(from, to)
            ){
                is Either.Success ->  {
                    _uiState.update { UiState.Empty }
                    _taskLiveData.update {
                            result.data.map {
                                it.toTaskItemUiState( onTaskCheck = { onCheckTask(it)})
                            }
                        }
                    }
                is Either.Failure -> _uiState.update {
                    UiState.Failure(result.cause as TaskException)
                }
            }
        }
    }

    fun insertTask(task: Task){
        viewModelScope.launch {
            when(val result = saveTask(task)
            ){
                is Either.Success -> {
                    _uiState.update { UiState.Success }
                    _taskLiveData.update { it.plus(task.toTaskItemUiState { onCheckTask(task) }) }
                }
                is Either.Failure -> _uiState.update {
                    UiState.Failure(result.cause as TaskException)
                }
            }
        }
    }

    fun onCheckTask(task:Task){
        task.changeDone()
        viewModelScope.launch {
            when(val result = updateTask(task)
            ){
                is Either.Success -> {
                    _taskLiveData.update {
                        it.map { currentTask->
                                if (currentTask.id == task.id) currentTask.copy(done = task.done)
                                else currentTask
                        }}
                }
                is Either.Failure -> _uiState.update {
                    UiState.Failure(result.cause as TaskException)
                }
            }
        }
    }

}
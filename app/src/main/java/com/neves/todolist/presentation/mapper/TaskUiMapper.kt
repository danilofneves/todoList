package com.neves.todolist.presentation.mapper

import com.neves.domain.model.Task
import com.neves.todolist.presentation.state.TaskItemUiState

fun TaskItemUiState.toTask() =
    Task(
        id,
        titulo,
        detalhe,
        data,
        done
    )

fun Task.toTaskItemUiState(onTaskCheck: ()->Unit) =
    TaskItemUiState(
        id,
        titulo,
        detalhe,
        data,
        done,
        onTaskCheck
    )
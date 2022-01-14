package com.neves.todolist.presentation.state

import java.util.*

data class TaskItemUiState (
    val id: Int,
    val titulo: String,
    val detalhe: String,
    val data: Date,
    var done: Boolean = false,
    val onTaskCheck: () -> Unit
)
package com.neves.todolist.presentation.state

sealed class UiState<out T> {
    data class Success<out T>(val value: T): UiState<T>()
    data class Failure(val exception:Exception): UiState<Nothing>()
    object Loading: UiState<Nothing>()
    object Empty: UiState<Nothing>()
}
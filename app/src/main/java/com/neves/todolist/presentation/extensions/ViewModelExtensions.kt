package com.neves.todolist.presentation.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neves.todolist.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

fun <T> ViewModel.launchOnUiState(uiState: MutableStateFlow<UiState<T>>, call: suspend () -> T, update: (UiState<T>, T) -> T) {
    uiState.update { UiState.Loading }
    viewModelScope.launch {
        try {
            val result = call.invoke()

            uiState.update { current ->
                UiState.Success(
                    update.invoke(current, result)
                )
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }
}

fun <T> ViewModel.launchOnUiState(uiState: MutableStateFlow<UiState<T>>, call: suspend () -> T) {
    uiState.update { UiState.Loading }
    viewModelScope.launch {
        try {
            val result = call.invoke()

            uiState.update {
                UiState.Success(
                    result
                )
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }
}
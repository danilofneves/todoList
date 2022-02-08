package com.neves.todolist.presentation.extensions

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.neves.data.local.exception.SaveLocalException
import com.neves.todolist.R
import com.neves.todolist.presentation.mapper.exception
import com.neves.todolist.presentation.state.UiState
import kotlinx.coroutines.flow.StateFlow

fun <T> Fragment.observerState(loadingView: View, uiState: StateFlow<UiState<T>>, onSuccess: (T) -> Unit) {
    lifecycle(uiState) { it ->
        loadingView.isVisible = false

        when(it){
            is UiState.Success -> {
                onSuccess(it.value)
            }
            is UiState.Failure -> {
                //ESSE TRATAMENTO AQUI PODERIA SER O DE SEM CONEXÃO
                if (it.exception is SaveLocalException) {
                    toast(getString(R.string.errorLocal))
                } else {
                    toast(exception(it.exception))
                }
            }
            is UiState.Loading -> loadingView.isVisible = true
            else -> Unit
        }
    }
}
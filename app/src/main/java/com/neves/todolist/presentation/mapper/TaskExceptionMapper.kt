package com.neves.todolist.presentation.mapper

import androidx.fragment.app.Fragment
import com.neves.domain.exception.TaskException
import com.neves.todolist.R

fun Fragment.exception(exception: Exception):String{
    return when(exception){
        is TaskException.EmptyTituloException -> getString(R.string.erroEmptyTituloTask)
        is TaskException.GeneralInsertException -> getString(R.string.erroTaskInsertGeneral)
        is TaskException.GeneralListException -> getString(R.string.erroTaskListGeneral)
        else -> String()
    }
}
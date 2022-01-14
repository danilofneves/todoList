package com.neves.domain.exception

sealed class TaskException () : Exception(){

    object EmptyTituloException: TaskException()

    object GeneralInsertException: TaskException()
    object GeneralListException: TaskException()
}
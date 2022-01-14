package com.neves.domain.model

import java.util.*

data class Task(
    val id: Int = 0,
    val titulo: String,
    val detalhe: String,
    val data: Date,
    var done: Boolean = false
){
    fun changeDone(){
        done = !done
    }
}
package com.neves.data.mapper

import com.neves.data.model.TaskRoom
import com.neves.domain.model.Task

fun Task.toTaskRoom() =
    TaskRoom(
        id,
        titulo,
        detalhe,
        data,
        done
    )

fun TaskRoom.toTask(): Task {
    return Task(
        id = id,
        titulo = titulo,
        detalhe = detalhe,
        data = data,
        done = done
    )
}
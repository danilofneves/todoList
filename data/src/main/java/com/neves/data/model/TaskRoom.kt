package com.neves.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neves.domain.model.Task
import java.util.*

@Entity
data class TaskRoom(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val detalhe: String,
    val data: Date,
    val done: Boolean = false
)
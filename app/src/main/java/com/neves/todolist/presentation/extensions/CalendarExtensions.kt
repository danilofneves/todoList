package com.neves.todolist.presentation.extensions

import java.util.*

fun Calendar.start(year:Int, month:Int, dayOfMonth:Int):Date{
    this.set(year, month, dayOfMonth, 0, 0, 0)
    return this.time
}

fun Calendar.end(year:Int, month:Int, dayOfMonth:Int):Date{
    this.set(year, month, dayOfMonth, 23, 59, 59)
    return this.time
}

fun Calendar.startToday():Date{
    this.set(this.get(Calendar.YEAR), this.get(Calendar.MONTH), this.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
    return this.time
}

fun Calendar.endToday():Date{
    this.set(this.get(Calendar.YEAR), this.get(Calendar.MONTH), this.get(Calendar.DAY_OF_MONTH), 23, 59, 59)
    return this.time
}
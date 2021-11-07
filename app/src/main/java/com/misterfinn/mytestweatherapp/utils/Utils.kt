package com.misterfinn.mytestweatherapp.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun String.getTime(): String = this.substring(11, 16)

fun Int.getDayOfWeek(): String {
    val stamp = Timestamp(this.toLong() * 1000)
    val date = Date(stamp.time)
    val pattern = "EEEE"
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(date)
}

fun String.makeFirstCharUpper(): String = this[0].uppercase() + this.substring(1, this.length)
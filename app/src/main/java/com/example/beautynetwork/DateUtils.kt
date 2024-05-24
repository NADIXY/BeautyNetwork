package com.example.beautynetwork

import java.text.SimpleDateFormat
import java.util.Date

object DateUtils {

    fun toSimpleString(date: Date) : String {
        val format = SimpleDateFormat("dd/MM/yyy HH:mm:ss")
        return format.format(date)
    }
}
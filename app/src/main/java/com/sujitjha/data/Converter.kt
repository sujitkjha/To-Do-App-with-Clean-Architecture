package com.sujitjha.data

import androidx.room.TypeConverter
import com.sujitjha.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority) :String{

        return priority.name

    }

    @TypeConverter
    fun toPriority(prority : String) : Priority {

        return Priority.valueOf(prority)

    }
}
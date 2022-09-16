package com.sujitjha.todo.data

import androidx.room.TypeConverter
import com.sujitjha.todo.data.models.Priority

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
package com.sujitjha.todo.data

import android.content.Context
import androidx.room.*
import com.sujitjha.todo.data.models.ToDoData

@Database(entities = [ToDoData::class], version = 2, exportSchema = false)

@TypeConverters(Converter::class)
abstract class ToDoDatabase :RoomDatabase() {

    abstract fun toDoDao() : ToDoDao

    companion object {
        @Volatile
        private var INSTANT :ToDoDatabase? =null

        fun getDatabase(context: Context) :ToDoDatabase{

            val tempInstant = INSTANT
            if (tempInstant!=null){
                return tempInstant
            }

            synchronized(lock = this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo_database"

                ).build()

                INSTANT =instance
                return instance
            }
        }
    }

}
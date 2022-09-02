package com.sujitjha.data.repository

import androidx.lifecycle.LiveData
import com.sujitjha.data.ToDoDao
import com.sujitjha.data.models.ToDoData

class ToDoRepository (private val toDoDao : ToDoDao){

    val getAllData : LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insertData(toDoData)
    }
}
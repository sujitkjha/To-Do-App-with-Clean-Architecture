package com.sujitjha.todo.data.repository

import androidx.lifecycle.LiveData
import com.sujitjha.todo.data.ToDoDao
import com.sujitjha.todo.data.models.ToDoData

class ToDoRepository (private val toDoDao : ToDoDao){

    val getAllData : LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData){
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData){
        toDoDao.deleteItem(toDoData)
    }

    suspend fun deleteAll(){
        toDoDao.deleteAll()
    }
}
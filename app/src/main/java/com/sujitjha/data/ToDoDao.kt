package com.sujitjha.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sujitjha.data.models.ToDoData


@Dao
interface ToDoDao {
    @Query("SELECT *FROM todo_table ORDER BY id ASC")
    fun getAllData() : LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)

    @Update (onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateData(toDoData: ToDoData)


}
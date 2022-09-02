package com.sujitjha.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.sujitjha.data.models.ToDoData


@Dao
interface ToDoDao {
    @Query("SELECT *FROM todo_table ORDER BY id ASC")
    fun getAllData() : LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)


}
package com.sujitjha.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sujitjha.data.models.Priority
import kotlinx.parcelize.Parcelize

@Entity(tableName ="todo_table")
@Parcelize
data class ToDoData(

    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var title: String,
    var priority: Priority,
    var description : String

) :Parcelable


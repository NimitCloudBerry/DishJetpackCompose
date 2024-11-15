package com.example.noshassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index


@Entity(tableName = "dishes", indices = [Index(value = ["dishName"], unique = true)])
data class Dish(
    val dishName: String,
    @PrimaryKey(autoGenerate = true)
    val dishId: Int,
    val imageUrl: String,
    val isPublished: Boolean,
    var scheduleTime: String?,
    var schedule:String?,

)




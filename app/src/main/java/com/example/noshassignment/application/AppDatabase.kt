package com.example.noshassignment.application

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noshassignment.database.DishDao
import com.example.noshassignment.model.Dish
import android.content.Context
import androidx.room.Room


@Database(entities = [Dish::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dishDao(): DishDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nosh_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
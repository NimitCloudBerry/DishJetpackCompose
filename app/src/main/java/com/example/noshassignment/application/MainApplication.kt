package com.example.noshassignment.application

import android.app.Application
import androidx.room.Room
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app_database"
        ).build()
    }

    companion object {
        lateinit var database: AppDatabase
    }
    }


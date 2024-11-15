package com.example.noshassignment.database

import android.app.Application
import androidx.room.Room
import com.example.noshassignment.application.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

   @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "dish_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDishDao(db: AppDatabase): DishDao {
        return db.dishDao()
    }
}

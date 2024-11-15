package com.example.noshassignment.repository

import com.example.noshassignment.database.DishDao
import com.example.noshassignment.model.Dish
import com.example.noshassignment.network.DishApiService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DishRepository @Inject constructor(private val dishApiService: DishApiService,
                                         private val dishDao: DishDao
) {
    suspend fun getDishes(): List<Dish> {
        return dishApiService.getDishes()
    }

    suspend fun insertDish(dish: Dish) {
        dishDao.insertDish(dish)
    }

    suspend fun getAllDishes(): List<Dish> {
        return dishDao.getAllDishes()
    }


    suspend fun updateDish(dish: Dish) {
        dishDao.updateDish(dish)
    }


    suspend fun deleteDish(dish: Dish) {
        dishDao.deleteDish(dish)
    }
}

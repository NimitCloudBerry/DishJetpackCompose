package com.example.noshassignment.network

import com.example.noshassignment.model.Dish
import retrofit2.http.GET


interface DishApiService {
    @GET("dev/nosh-assignment")
    suspend fun getDishes(): List<Dish>
}

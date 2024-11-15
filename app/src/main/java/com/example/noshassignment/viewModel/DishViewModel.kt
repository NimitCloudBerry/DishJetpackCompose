package com.example.noshassignment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noshassignment.model.Dish
import com.example.noshassignment.repository.DishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DishViewModel @Inject constructor(private val dishRepository: DishRepository) : ViewModel() {

    private val _recommendedDishes = MutableLiveData<List<Dish>>(emptyList())
    val recommendedDishes: LiveData<List<Dish>> = _recommendedDishes

    private val _savedDishes = MutableLiveData<List<Dish>>(emptyList())
    val savedDishes: LiveData<List<Dish>> = _savedDishes

    init {
        fetchRecommendations() // Fetch recommendations on initialization
    }

    private fun fetchRecommendations() {
        viewModelScope.launch {
            try {
                _recommendedDishes.value = dishRepository.getDishes() // Fetch from API
            } catch (e: Exception) {
                _recommendedDishes.value = emptyList()
            }
        }
    }

    fun saveDish(dish: Dish) {
        viewModelScope.launch {
            dishRepository.insertDish(dish) // Save selected dish to database
            fetchSavedDishes() // Refresh saved dishes
        }
    }

    fun fetchSavedDishes() {
        viewModelScope.launch {
            _savedDishes.value = dishRepository.getAllDishes() // Fetch from database
        }
    }

    // Update an existing dish in the database
    fun updateDish(dish: Dish) {
        viewModelScope.launch {
            dishRepository.updateDish(dish) // Update dish in the database
            fetchSavedDishes() // Refresh saved dishes
        }
    }

    // Delete a dish from the database
    fun deleteDish(dish: Dish) {
        viewModelScope.launch {
            dishRepository.deleteDish(dish) // Delete dish from the database
            fetchSavedDishes() // Refresh saved dishes
        }
    }
}


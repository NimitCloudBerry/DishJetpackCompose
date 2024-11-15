package com.example.noshassignment.model

class NoshDishResponse {
    companion object{
        val dishes = listOf(
            Dish("Jeera Rice", 1, "https://nosh-assignment.s3.ap-south-1.amazonaws.com/jeera-rice.jpg", true, "0", null),
            Dish( "Paneer Tikka", 2, "https://nosh-assignment.s3.ap-south-1.amazonaws.com/paneer-tikka.jpg", true, "0", null),
            Dish("Rabdi", 3, "https://nosh-assignment.s3.ap-south-1.amazonaws.com/rabdi.jpg", true, "0", null),
            Dish( "Chicken Biryani", 4, "https://nosh-assignment.s3.ap-south-1.amazonaws.com/chicken-biryani.jpg", true, "0", null),
            Dish( "Alfredo Pasta", 5, "https://nosh-assignment.s3.ap-south-1.amazonaws.com/alfredo-pasta.jpg", true, "0", null)
        )
    }
}
package com.example.appconapi.Data.Model

data class Product(
    val id: Int,
    val title: String,          // Como strMeal
    val description: String,    // Descripción adicional
    val category: String,       // Como strCategory
    val price: Double,             // Precio
    val brand: String?,         // Como strArea (marca)
    val thumbnail: String       // Como strMealThumb
)

data class ProductsResponse(
    val products: List<Product>
)
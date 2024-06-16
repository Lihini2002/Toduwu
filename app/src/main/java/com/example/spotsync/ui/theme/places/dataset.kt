package com.example.spotsync.ui.theme.places


data class location(
    val title: String,
    val distance: String,
    val todosCount: Int,
    val iconResName: String, // Material Icon name
    val usageCount: Int
)

val placesExample = listOf(
    location(
        title = "Grocery Store",
        distance = "2 km",
        todosCount = 2,
        iconResName = "shopping_cart", // Material Icon name for shopping cart
        usageCount = 5
    ),
    location(
        title = "Home",
        distance = "1 km",
        todosCount = 1,
        iconResName = "home", // Material Icon name for home
        usageCount = 10
    ),
    location(
        title = "Office",
        distance = "5 km",
        todosCount = 2,
        iconResName = "work", // Material Icon name for work
        usageCount = 8
    ),
    location(
        title = "Gym",
        distance = "3 km",
        todosCount = 3,
        iconResName = "fitness_center", // Material Icon name for fitness center
        usageCount = 15
    ),
    location(
        title = "Library",
        distance = "4 km",
        todosCount = 1,
        iconResName = "library", // Material Icon name for library
        usageCount = 7
    ),
    location(
        title = "Coffee Shop",
        distance = "2.5 km",
        todosCount = 4,
        iconResName = "local_cafe", // Material Icon name for local cafe
        usageCount = 12
    ),
    // Add more places as needed
)

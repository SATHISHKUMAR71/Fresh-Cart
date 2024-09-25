package com.example.shoppinggroceryapp.model.entities.products

import androidx.room.Embedded

data class ProductWithCategory(
    @Embedded val product: Product,
    @Embedded val category: Category,
)
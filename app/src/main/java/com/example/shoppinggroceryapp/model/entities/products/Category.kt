package com.example.shoppinggroceryapp.model.entities.products

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity

data class Category(
    @PrimaryKey
    val categoryName:String,
    val parentCategoryName:String,
    val categoryDescription:String
)
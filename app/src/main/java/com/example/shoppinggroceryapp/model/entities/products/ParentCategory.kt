package com.example.shoppinggroceryapp.model.entities.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParentCategory(
    @PrimaryKey
    val parentCategoryName:String,
    val parentCategoryImage:String,
    val parentCategoryDescription:String,
    val isEssential:Boolean
)
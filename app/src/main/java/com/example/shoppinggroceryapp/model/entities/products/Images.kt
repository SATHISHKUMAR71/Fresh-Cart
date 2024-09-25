package com.example.shoppinggroceryapp.model.entities.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Images (
    @PrimaryKey(autoGenerate = true)
    val imageId:Long,
    val productId:Long,
    val images:String
)
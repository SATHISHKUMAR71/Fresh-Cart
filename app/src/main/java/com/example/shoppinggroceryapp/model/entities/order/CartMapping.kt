package com.example.shoppinggroceryapp.model.entities.order

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartMapping(
    @PrimaryKey(autoGenerate = true)
    val cartId:Int,
    val userId:Int,
    val status:String
)

package com.example.shoppinggroceryapp.model.entities.order

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["cartId","productId"])
data class Cart(
    val cartId:Int,
    val productId:Int,
    val totalItems:Int,
    val unitPrice:Float
)
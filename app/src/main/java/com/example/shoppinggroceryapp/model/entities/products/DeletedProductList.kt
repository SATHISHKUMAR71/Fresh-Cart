package com.example.shoppinggroceryapp.model.entities.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeletedProductList(
    @PrimaryKey(autoGenerate = false)
    val productId:Long,
    val brandId:Long,
    val categoryName:String,
    val productName:String,
    val productDescription:String,
    val price:Float,
    val offer:Float,
    val productQuantity:String,
    val mainImage:String,
    val isVeg:Boolean,
    val manufactureDate:String,
    val expiryDate:String,
    val availableItems:Int
)
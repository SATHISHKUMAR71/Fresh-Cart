package com.example.shoppinggroceryapp.model.entities.products

data class CartWithProductData (
    val mainImage:String?,
    val productName:String,
    val productDescription:String,
    val totalItems:Int,
    val unitPrice:Float,
    val manufactureDate:String,
    val expiryDate:String,
    val productQuantity:String,
    var brandName:String
)
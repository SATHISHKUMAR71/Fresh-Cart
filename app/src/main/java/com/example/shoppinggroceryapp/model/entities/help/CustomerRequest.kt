package com.example.shoppinggroceryapp.model.entities.help

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CustomerRequest(
    @PrimaryKey(autoGenerate = true)
    val helpId:Int,
    val userId:Int,
    val requestedDate:String,
    val orderId:Int,
    val request:String
)
package com.example.shoppinggroceryapp.model.entities.deals

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Deals (
    @PrimaryKey(autoGenerate = true)
    val dealId:Long,
    val productId:Long,
    val dealImage:String,
    val dealDescription:String,
    val extraText:String,
)
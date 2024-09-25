package com.example.shoppinggroceryapp.model.entities.help

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FAQ (
    @PrimaryKey(autoGenerate = true)
    val faqId:Int,
    val question:String,
    val answer:String
)
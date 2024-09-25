package com.example.shoppinggroceryapp.model.entities.products

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class BrandData(
    @PrimaryKey(autoGenerate = true)
    var brandId:Long,
    var brandName:String
)
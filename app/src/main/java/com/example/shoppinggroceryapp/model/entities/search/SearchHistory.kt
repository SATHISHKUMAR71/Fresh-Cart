package com.example.shoppinggroceryapp.model.entities.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistory (
    @PrimaryKey(autoGenerate = false)
    var searchText:String,
    var userId:Int
)
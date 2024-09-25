package com.example.shoppinggroceryapp.model.entities.recentlyvieweditems

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentlyViewedItems(
    @PrimaryKey(autoGenerate = true)
    var recentlyViewedId:Long,
    var userId:Int,
    var productId:Long
)
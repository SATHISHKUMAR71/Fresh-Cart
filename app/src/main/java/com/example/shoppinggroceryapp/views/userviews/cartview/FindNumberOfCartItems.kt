package com.example.shoppinggroceryapp.views.userviews.cartview

import androidx.lifecycle.MutableLiveData

class FindNumberOfCartItems {
    companion object{
        var productCount: MutableLiveData<Int> = MutableLiveData(0)
    }
}
package com.example.shoppinggroceryapp.views

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.viewmodel.productviewmodel.ProductListViewModel

class FindNumberOfCartItems {
    companion object{
        var productCount: MutableLiveData<Int> = MutableLiveData(0)

        fun initCart(cartId:Int,context: Context){
            var cartListViewModel = ProductListViewModel(AppDatabase.getAppDatabase(context).getUserDao())
            cartListViewModel.getCartItems(cartId)
        }
    }
}
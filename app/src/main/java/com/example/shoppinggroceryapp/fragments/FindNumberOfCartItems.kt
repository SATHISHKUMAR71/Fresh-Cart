package com.example.shoppinggroceryapp.fragments

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.viewmodel.cartviewmodel.CartViewModel
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
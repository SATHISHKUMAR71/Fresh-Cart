package com.example.shoppinggroceryapp.viewmodel.orderviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.products.CartWithProductData

class OrderSummaryViewModel(var userDao: UserDao):ViewModel() {

    var cartItems:MutableLiveData<List<CartWithProductData>> = MutableLiveData()
    fun getProductsWithCartId(cartId:Int){
        Thread{
            cartItems.postValue(userDao.getProductsWithCartId(cartId))
        }.start()
    }
}
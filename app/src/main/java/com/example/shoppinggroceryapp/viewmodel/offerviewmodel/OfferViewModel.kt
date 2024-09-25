package com.example.shoppinggroceryapp.viewmodel.offerviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.products.Product

class OfferViewModel(var userDao: UserDao):ViewModel() {
    var offeredProductList:MutableLiveData<List<Product>> = MutableLiveData()
    fun getOfferedProducts(){
        Thread {
            offeredProductList.postValue(userDao.getOfferedProducts())
        }.start()
    }
}
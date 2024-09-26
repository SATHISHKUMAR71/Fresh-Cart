package com.example.shoppinggroceryapp.views.userviews.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.model.dao.ProductDao
import com.example.shoppinggroceryapp.model.entities.products.Product

class HomeViewModel(var productDao: ProductDao):ViewModel() {

    var recentlyViewedList:MutableLiveData<MutableList<Product>> = MutableLiveData()
    fun getRecentlyViewedItems(){
        Thread{
            val list = mutableListOf<Product>()
            val recentlyViewedProduct = productDao.getRecentlyViewedProducts(MainActivity.userId.toInt())
            for(i in recentlyViewedProduct){
                var product:Product? = productDao.getProductById(i.toLong())
                product?.let {
                    list.add(it)
                }
            }
            list.reverse()
            recentlyViewedList.postValue(list)
        }.start()
    }
}
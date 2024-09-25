package com.example.shoppinggroceryapp.viewmodel.homeviewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.model.dao.ProductDao
import com.example.shoppinggroceryapp.model.database.AppDatabase
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
//                if(product==null){
//                    var deletedProduct = productDao.getDeletedProductById(i.toLong())
//                    deletedProduct.let {
//                        list.add(Product(productId = it.productId, brandId =it.brandId,
//                            categoryName = it.categoryName, productName = it.productName,
//                            productDescription = it.productDescription, price = it.price,
//                            offer = it.offer, productQuantity = it.productQuantity, mainImage = it.mainImage,
//                            isVeg = it.isVeg, manufactureDate = it.manufactureDate, expiryDate = it.expiryDate,
//                            availableItems = it.availableItems))
//                    }
//
//                }
//                else{
//                    list.add(product)
//                }
            }
            list.reverse()
            recentlyViewedList.postValue(list)
        }.start()
    }
}
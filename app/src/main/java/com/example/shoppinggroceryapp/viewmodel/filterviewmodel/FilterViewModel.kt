package com.example.shoppinggroceryapp.viewmodel.filterviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.fragments.appfragments.OfferFragment
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.products.Product

class FilterViewModel(var userDao: UserDao):ViewModel() {



    fun filterList(productList:List<Product>,offer:Float):List<Product>{
        return productList.filter { it.offer>=offer }
    }

    fun filterListBelow(productList:List<Product>,offer:Float):List<Product>{
        return productList.filter { it.offer<offer }
    }

}
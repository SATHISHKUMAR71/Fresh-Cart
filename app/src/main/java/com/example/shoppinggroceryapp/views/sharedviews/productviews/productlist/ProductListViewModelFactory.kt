package com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.UserDao

class ProductListViewModelFactory(var userDao: UserDao):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductListViewModel(userDao) as T
    }

}
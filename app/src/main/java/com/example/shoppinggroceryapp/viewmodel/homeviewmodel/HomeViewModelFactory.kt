package com.example.shoppinggroceryapp.viewmodel.homeviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.ProductDao

class HomeViewModelFactory(var productDao: ProductDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(productDao) as T
    }
}
package com.example.shoppinggroceryapp.viewmodel.categoryviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.ProductDao
import com.example.shoppinggroceryapp.model.dao.UserDao

class CategoryViewModelFactory(var productDao: ProductDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(productDao) as T
    }
}
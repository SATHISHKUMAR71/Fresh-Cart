package com.example.shoppinggroceryapp.views.userviews.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.ProductDao

class CategoryViewModelFactory(var productDao: ProductDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(productDao) as T
    }
}
package com.example.shoppinggroceryapp.views.retailerviews.addeditproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.ProductDao
import com.example.shoppinggroceryapp.model.dao.RetailerDao

class AddEditProductViewModelFactory(var retailerDao: RetailerDao, var productDao: ProductDao):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddEditProductViewModel(retailerDao,productDao) as T
    }
}
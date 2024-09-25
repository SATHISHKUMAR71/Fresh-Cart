package com.example.shoppinggroceryapp.viewmodel.retailerviewmodel.inventoryviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.ProductDao
import com.example.shoppinggroceryapp.model.dao.RetailerDao

class AddEditViewModelFactory(var retailerDao: RetailerDao,var productDao: ProductDao):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddEditViewModel(retailerDao,productDao) as T
    }
}
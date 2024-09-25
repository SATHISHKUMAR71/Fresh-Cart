package com.example.shoppinggroceryapp.viewmodel.orderviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.dao.UserDao

class OrderSuccessViewModelFactory(var retailerDao: RetailerDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderSuccessViewModel(retailerDao) as T
    }
}
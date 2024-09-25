package com.example.shoppinggroceryapp.viewmodel.orderviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.UserDao

class OrderSummaryViewModelFactory(var userDao: UserDao):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderSummaryViewModel(userDao) as T
    }
}
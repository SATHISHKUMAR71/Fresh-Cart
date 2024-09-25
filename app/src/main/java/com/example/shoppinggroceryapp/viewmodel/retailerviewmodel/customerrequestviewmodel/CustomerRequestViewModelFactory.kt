package com.example.shoppinggroceryapp.viewmodel.retailerviewmodel.customerrequestviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.UserDao

class CustomerRequestViewModelFactory(var userDao: UserDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomerRequestViewModel(userDao) as T
    }
}
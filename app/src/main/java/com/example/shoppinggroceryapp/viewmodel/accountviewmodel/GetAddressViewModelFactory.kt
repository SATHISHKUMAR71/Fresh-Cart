package com.example.shoppinggroceryapp.viewmodel.accountviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.UserDao

class GetAddressViewModelFactory(var userDao: UserDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return com.example.shoppinggroceryapp.viewmodel.accountviewmodel.GetAddressViewModel(userDao) as T
    }
}
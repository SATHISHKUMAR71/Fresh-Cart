package com.example.shoppinggroceryapp.viewmodel.accountviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.UserDao

class HelpViewModelFactory(var userDao: UserDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return com.example.shoppinggroceryapp.viewmodel.accountviewmodel.HelpViewModel(userDao) as T
    }
}
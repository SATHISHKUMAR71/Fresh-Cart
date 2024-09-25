package com.example.shoppinggroceryapp.viewmodel.cartviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.UserDao

class CartViewModelFactory(var userDao: UserDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(userDao) as T
    }
}
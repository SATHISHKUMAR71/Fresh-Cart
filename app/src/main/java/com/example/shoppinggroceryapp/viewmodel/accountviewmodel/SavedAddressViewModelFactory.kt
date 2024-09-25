package com.example.shoppinggroceryapp.viewmodel.accountviewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.UserDao

class SavedAddressViewModelFactory(var userDao: UserDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SavedAddressViewModel(userDao) as T
    }
}
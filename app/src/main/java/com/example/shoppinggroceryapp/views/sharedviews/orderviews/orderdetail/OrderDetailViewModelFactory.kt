package com.example.shoppinggroceryapp.views.sharedviews.orderviews.orderdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.RetailerDao

class OrderDetailViewModelFactory(var retailerDao: RetailerDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderDetailViewModel(
            retailerDao
        ) as T
    }
}
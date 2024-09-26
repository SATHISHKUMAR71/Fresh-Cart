package com.example.shoppinggroceryapp.views.sharedviews.orderviews.orderlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.RetailerDao

class OrderListViewModelFactory(var retailerDao: RetailerDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderListViewModel(
            retailerDao
        ) as T
    }
}
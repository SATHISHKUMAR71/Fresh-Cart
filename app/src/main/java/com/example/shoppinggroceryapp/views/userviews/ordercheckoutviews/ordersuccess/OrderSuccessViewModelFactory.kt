package com.example.shoppinggroceryapp.views.userviews.ordercheckoutviews.ordersuccess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.RetailerDao

class OrderSuccessViewModelFactory(var retailerDao: RetailerDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderSuccessViewModel(retailerDao) as T
    }
}
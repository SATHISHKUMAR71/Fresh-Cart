package com.example.shoppinggroceryapp.views.userviews.ordercheckoutviews.ordersummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.dao.UserDao

class OrderSummaryViewModelFactory(var retailerDao: RetailerDao):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderSummaryViewModel(retailerDao) as T
    }
}
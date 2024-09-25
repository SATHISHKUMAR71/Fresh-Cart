package com.example.shoppinggroceryapp.viewmodel.accountviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.help.CustomerRequest

class HelpViewModel(var userDao: UserDao):ViewModel() {
    var productList:MutableLiveData<String> = MutableLiveData()

    fun assignProductList(selectedCartId:Int){
        Thread {
            var cartItems = userDao.getProductsWithCartId(selectedCartId)
            var value = productList.value?:""
            for (i in cartItems) {
                 value += i.productName + " (${i.totalItems}) "
            }
            productList.postValue(value)
        }.start()
    }

    fun sendReq(customerRequest: CustomerRequest){
        Thread{
            userDao.addCustomerRequest(customerRequest)
        }.start()
    }
}
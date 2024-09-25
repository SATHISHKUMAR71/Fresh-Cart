package com.example.shoppinggroceryapp.viewmodel.retailerviewmodel.customerrequestviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.dataclass.CustomerRequestWithName
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails
import com.example.shoppinggroceryapp.model.entities.products.CartWithProductData

class CustomerRequestViewModel(var userDao: UserDao):ViewModel() {

    var customerRequestList:MutableLiveData<List<CustomerRequestWithName>> = MutableLiveData()
    var selectedOrderLiveData:MutableLiveData<OrderDetails> = MutableLiveData()
    var correspondingCartLiveData:MutableLiveData<List<CartWithProductData>> = MutableLiveData()

    fun getCustomerRequest(){
        Thread {
            customerRequestList.postValue(userDao.getDataFromCustomerReqWithName())
        }.start()
    }

    fun getOrderDetails(orderId:Int){
        Thread {
            selectedOrderLiveData.postValue(userDao.getOrderDetails(orderId))
        }.start()
    }

    fun getCorrespondingCart(cartId:Int){
        Thread {
                correspondingCartLiveData.postValue(userDao.getProductsWithCartId(cartId))
        }.start()
    }
}
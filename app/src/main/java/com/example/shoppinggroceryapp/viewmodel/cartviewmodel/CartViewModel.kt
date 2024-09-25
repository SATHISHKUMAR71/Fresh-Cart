package com.example.shoppinggroceryapp.viewmodel.cartviewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.model.entities.user.Address

class CartViewModel(var userDao: UserDao):ViewModel() {

    var cartProducts:MutableLiveData<List<Product>> = MutableLiveData()
    var totalPrice:MutableLiveData<Float> = MutableLiveData()
    var addressList:MutableLiveData<List<Address>> = MutableLiveData()
    fun getProductsByCartId(cartId:Int){
        Thread{
            cartProducts.postValue(userDao.getProductsByCartId(cartId))
        }.start()
    }


    fun calculateInitialPrice(cartId: Int){
        Thread{
            val list = userDao.getCartItems(cartId)
            var price = 49f
            for(i in list){
                price += (i.unitPrice*i.totalItems)
            }
            totalPrice.postValue(price)
        }.start()
    }

    fun getAddressListForUser(userId:Int){
        Thread{
            addressList.postValue(userDao.getAddressListForUser(userId))
        }.start()
    }
}
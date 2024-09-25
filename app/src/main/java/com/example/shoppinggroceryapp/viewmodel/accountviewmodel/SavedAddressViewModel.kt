package com.example.shoppinggroceryapp.viewmodel.accountviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.user.Address

class SavedAddressViewModel(var userDao: UserDao):ViewModel() {

    var addressList:MutableLiveData<List<Address>> = MutableLiveData()
    fun getAddressListForUser(userId:Int){
        Thread{
            addressList.postValue(userDao.getAddressListForUser(userId))
        }.start()
    }
}
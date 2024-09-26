package com.example.shoppinggroceryapp.views.sharedviews.authenticationviews.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.order.CartMapping
import com.example.shoppinggroceryapp.model.entities.user.User

class LoginViewModel(var userDao: UserDao) :ViewModel(){
    var user:MutableLiveData<User> = MutableLiveData()
    var userName:MutableLiveData<User> = MutableLiveData()

    fun isUser(userData:String){
        Thread{
            userName.postValue(userDao.getUserData(userData))
        }.start()
    }

    fun validateUser(email:String,password:String){
        Thread {
            user.postValue(userDao.getUser(email, password))
        }.start()
    }

    fun assignCartForUser(){
        Thread{
            val cart:CartMapping? = userDao.getCartForUser(user.value?.userId?:-1)
            if(cart==null){
               userDao.addCartForUser(CartMapping(0,user.value?.userId?:-1,"available"))
               val newCart:CartMapping? =  userDao.getCartForUser(user.value?.userId?:-1)
                while (newCart==null) {
                }
                MainActivity.cartId = newCart.cartId
            }
            else{
                MainActivity.cartId = cart.cartId
            }
        }.start()
    }
}
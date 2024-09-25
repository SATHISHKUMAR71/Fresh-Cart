package com.example.shoppinggroceryapp.viewmodel.accountviewmodel

import android.provider.ContactsContract.CommonDataKinds.Phone
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.model.entities.user.User

class EditProfileViewModel(var userDao: UserDao):ViewModel() {

    var recentlyBoughtList:MutableLiveData<MutableList<Product>> = MutableLiveData()
    var user:MutableLiveData<User> = MutableLiveData()
    fun saveDetails(oldEmail:String,firstName:String,lastName:String,email:String,phone: String,image:String){
        Thread {
            val user = userDao.getUserData(oldEmail)
            val userTmp = User(
                userId = user.userId,
                userImage = image,
                userFirstName = firstName,
                userLastName = lastName,
                userEmail = email,
                userPhone = phone,
                userPassword = user.userPassword,
                dateOfBirth = user.dateOfBirth,
                isRetailer = user.isRetailer)
            println(userTmp)
            userDao.updateUser(userTmp)
        }.start()
    }

    fun saveUserImage(oldEmail:String,mainImage:String){
        Thread {
            val user = userDao.getUserData(oldEmail)
            userDao.updateUser(User(
                userId = user.userId,
                userImage = mainImage,
                userFirstName = user.userFirstName,
                userLastName = user.userLastName,
                userEmail = user.userEmail,
                userPhone = user.userPhone,
                userPassword = user.userPassword,
                dateOfBirth = user.dateOfBirth,
                isRetailer = user.isRetailer
            ))
        }.start()
    }

    fun getPurchasedProducts(userId:Int){
        Thread{
            val list = mutableListOf<Product>()
            for(i in userDao.getBoughtProductsList(userId)){
                for(j in userDao.getProductsByCartId(i.cartId)){
                    if(j !in list){
                        list.add(j)
                    }
                }
            }
            list.reverse()
            recentlyBoughtList.postValue(list)
        }.start()
    }

    fun getUser(emailOrPhone:String){
        Thread{
            user.postValue(userDao.getUserData(emailOrPhone))
        }.start()
    }

    fun savePassword(user:User){
        Thread {
            userDao.updateUser(user)
        }.start()
    }
}
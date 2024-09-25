package com.example.shoppinggroceryapp.viewmodel.authenticationviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.user.User

class SignUpViewModel(var userDao: UserDao):ViewModel() {
    var registrationStatus:MutableLiveData<Boolean> = MutableLiveData()
    var registrationStatusInt:MutableLiveData<Int> = MutableLiveData()

    fun registerNewUser(user: User){
        Thread{
            val email = userDao.getUserData(user.userEmail)
            val phone = userDao.getUserData(user.userPhone)
            if(phone==null&&email==null) {
                Thread {
                    var id = userDao.addUser(user)
                    var userEmail = user.userEmail
                    var userPhone = user.userPhone
                    println("USER ADDED:  ${user.userEmail} ${user.userPhone}\n")
                    println("USER ADDED GOT: by ID ${userDao.getUserById(id.toInt())}\n get by Phone ${userDao.getUserByPhone(userPhone)}\n get by email ${userDao.getUserByEmail(user.userEmail)}\nget by phone same method: ${userDao.getUserData(userPhone)}\n" +
                            "get by Email same method: ${userDao.getUserData(userEmail)}\nget both: values${userDao.getUser(userEmail,user.userPassword)}\n INPUT DATA $user")
                    registrationStatusInt.postValue(0)
                    registrationStatus.postValue(true)
                }.start()
            }
            else if(phone!=null && email!=null){
                registrationStatusInt.postValue(1)
//                registrationStatus.postValue(false)
            }
            else if(phone!=null){
                registrationStatusInt.postValue(2)
            }
            else{
                registrationStatusInt.postValue(3)
            }
        }.start()
    }
}